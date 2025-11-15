package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.dto.order.CheckoutDTO;
import com.example.PCOnlineShop.dto.warranty.WarrantyDetailDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.cart.CartItem; // ✅ Thêm
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PayOS payOS;

    // (Static Map... giữ nguyên)
    private static final Map<Integer, Integer> WARRANTY_MONTHS_BY_CATEGORY;
    static {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 12); map.put(2, 36); map.put(3, 24); map.put(4, 12); map.put(5, 12);
        map.put(6, 6);  map.put(7, 12); map.put(8, 12); map.put(9, 6);  map.put(10, 6);
        WARRANTY_MONTHS_BY_CATEGORY = Collections.unmodifiableMap(map);
    }

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,

                        ProductRepository productRepository,
                        PaymentRepository paymentRepository,
                        PayOS payOS) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.payOS = payOS;
    }

    /**
     * TẠO ĐƠN HÀNG DUY NHẤT
     */
    @Transactional
    public Order createOrder(Account customerAccount, Map<Integer, CartItem> cartItems,
                             CheckoutDTO checkoutDTO) {

        Order order = new Order();
        order.setAccount(customerAccount);
        order.setCreatedDate(new Date());
        order.setStatus("Pending Payment");

        order.setShippingMethod(checkoutDTO.getShippingMethod());
        order.setNote(checkoutDTO.getNote());
        order.setShippingFullName(checkoutDTO.getShippingFullName());
        order.setShippingPhone(checkoutDTO.getShippingPhone());
        order.setShippingAddress(checkoutDTO.getShippingAddress());

        List<OrderDetail> orderDetails = new ArrayList<>();
        double calculatedFinalAmount = 0.0;

        for (Map.Entry<Integer, CartItem> entry : cartItems.entrySet()) {
            CartItem item = entry.getValue();
            Product product = item.getProduct();
            int quantityToBuy = item.getQuantity();

            // LOGIC KHO KÉP:

            if (!item.isBuildItem()) {
                int currentInventory = product.getInventoryQuantity();
                if (currentInventory < quantityToBuy) {
                    throw new IllegalStateException("Product " + product.getProductName() + " not enough inventory.");
                }
                product.setInventoryQuantity(currentInventory - quantityToBuy);
                productRepository.save(product);
            }

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(quantityToBuy);
            detail.setPrice(product.getPrice());
            orderDetails.add(detail);
            calculatedFinalAmount += (product.getPrice() * quantityToBuy);
        }

        order.setFinalAmount(calculatedFinalAmount);
        order.setOrderDetails(orderDetails);
        return orderRepository.save(order);
    }

    /**
     * Hàm Hoàn kho
     */
    private void rollBackInventory(Order order) {
        List<OrderDetail> details = orderDetailRepository.findByOrder(order);
        for (OrderDetail detail : details) {
            Product product = detail.getProduct();
            if (product != null) {
                // if (!detail.isBuildItem()) {
                product.setInventoryQuantity(product.getInventoryQuantity() + detail.getQuantity());
                productRepository.save(product);
                // }
            }
        }
    }
    /**
     * Logic Hủy đơn
     */
    @Transactional
    public void cancelOrderFromPaymentId(long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("No payment found: " + paymentId));
        Order order = payment.getOrder();

        if ("Pending Payment".equals(order.getStatus()) && "PENDING".equals(payment.getStatus())) {
            payment.setStatus("CANCELLED");
            order.setStatus("Cancelled");
            order.setPaymentStatus("CANCELLED");

            rollBackInventory(order);

            try {
                if (payment.getOrderCode() != null) {
                    payOS.paymentRequests().cancel(payment.getOrderCode(), "Cancelled by customer or out of payment time");
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi hủy link PayOS: " + e.getMessage());
            }

            paymentRepository.save(payment);
            orderRepository.save(order);
        }
    }


    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }
    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }
    public List<OrderDetail> getOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Đơn hàng không tồn tại: " + orderId));
        return orderDetailRepository.findByOrder(order);
    }
    public List<Order> findAllOrdersForAdmin() {
        return orderRepository.findAllByRole(RoleName.Customer);
    }
    public List<Order> getOrdersByPhoneNumberForWarranty(String phoneNumber) {
        return orderRepository.findByAccount_PhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public List<WarrantyDetailDTO> getWarrantyDetailsByOrderId(long orderId) {
        List<OrderDetail> details = orderDetailRepository.findByOrder_OrderIdWithAssociations(orderId);
        LocalDate today = LocalDate.now();

        return details.stream()
                .map(detail -> {
                    Order order = detail.getOrder();
                    Product product = detail.getProduct();
                    Category category = (product != null) ? product.getCategories().get(0) : null;
                    Date createdDate = order.getCreatedDate();
                    if (order == null || product == null || category == null || createdDate == null) {
                        return null;
                    }
                    int categoryId = category.getCategoryId();
                    int warrantyMonths = WARRANTY_MONTHS_BY_CATEGORY.getOrDefault(categoryId, 0);

                    LocalDate orderLocalDate = createdDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalDate expiryDate = orderLocalDate.plusMonths(warrantyMonths);

                    String warrantyStatus;
                    long daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate);

                    if (daysUntilExpiry < 0) warrantyStatus = "Expired";
                    else if (daysUntilExpiry <= 7) warrantyStatus = "Expiring Soon";
                    else warrantyStatus = "Active";

                    return new WarrantyDetailDTO(
                            order.getOrderId(),
                            product.getProductName(),
                            createdDate,
                            warrantyMonths,
                            expiryDate,
                            warrantyStatus
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional(readOnly = true)
    public List<Order> getShippingQueueOrders() {
        List<String> shippingStatuses = List.of("Ready to Ship", "Delivering");
        return orderRepository.findByStatusIn(shippingStatuses);
    }

    @Transactional
    public void updateShippingStatus(long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));

        String currentStatus = order.getStatus();
        boolean isValidTransition = false;
        Date now = new Date();

        if ("Ready to Ship".equals(currentStatus) && List.of("Delivering", "Completed", "Cancelled").contains(newStatus)) {
            isValidTransition = true;
            if ("Delivering".equals(newStatus)) {
                order.setReadyToShipDate(now);
            }
        } else if ("Delivering".equals(currentStatus) && List.of("Completed", "Cancelled", "Delivery Failed").contains(newStatus)) {
            isValidTransition = true;
        }

        if (!isValidTransition) {
            System.err.println("Attempted invalid status transition from '" + currentStatus + "' to '" + newStatus + "' on shipping screen.");
            return;
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}