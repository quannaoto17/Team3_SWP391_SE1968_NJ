package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.dto.order.CheckoutDTO;
import com.example.PCOnlineShop.dto.order.CheckoutPageDTO;
import com.example.PCOnlineShop.dto.warranty.WarrantyDetailDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.model.cart.CartItem;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import com.example.PCOnlineShop.service.address.AddressService;
import com.example.PCOnlineShop.service.cart.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
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
    private final CartService cartService;
    private final AddressService addressService;

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
                        PayOS payOS,
                        @Lazy CartService cartService,
                        AddressService addressService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.payOS = payOS;
        this.cartService = cartService;
        this.addressService = addressService;
    }

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

    public Order getOrderDetailForView(long orderId, Account currentAccount, boolean isAdmin) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!isAdmin) {
            if (currentAccount == null || order.getAccount() == null ||
                    order.getAccount().getAccountId() != currentAccount.getAccountId()) {
                throw new SecurityException("You are not authorized to view this order.");
            }
        }
        return order;
    }

    public CheckoutPageDTO prepareCheckoutData(Account account) {
        List<CartItemDTO> selectedItems = cartService.getCartItems(account)
                .stream().filter(CartItemDTO::isSelected).toList();

        if (selectedItems.isEmpty()) {
            throw new IllegalStateException("No chosen products to checkout.");
        }

        double total = selectedItems.stream().mapToDouble(CartItemDTO::getSubtotal).sum();

        List<Address> addresses = addressService.getAddressesForAccount(account);
        Address defaultAddr = addressService.getDefaultAddress(account)
                .orElse(addresses.isEmpty() ? null : addresses.get(0));

        CheckoutDTO dto = new CheckoutDTO();
        dto.setShippingMethod("Giao hàng tận nơi");
        if (defaultAddr != null) {
            dto.setShippingFullName(defaultAddr.getFullName());
            dto.setShippingPhone(defaultAddr.getPhone());
            dto.setShippingAddress(defaultAddr.getAddress());
        }

        return new CheckoutPageDTO(dto, selectedItems, total, addresses, defaultAddr);
    }

    @Transactional
    public Order processCheckout(Account account, CheckoutDTO checkoutDTO) {
        Map<Integer, CartItem> checkoutMap = cartService.getCartMapForCheckout(account);
        if (checkoutMap.isEmpty()) {
            throw new IllegalStateException("Please choose products!");
        }
        return createOrder(account, checkoutMap, checkoutDTO);
    }

    private void rollBackInventory(Order order) {
        List<OrderDetail> details = orderDetailRepository.findByOrder(order);
        for (OrderDetail detail : details) {
            Product product = detail.getProduct();
            if (product != null) {
                product.setInventoryQuantity(product.getInventoryQuantity() + detail.getQuantity());
                productRepository.save(product);
            }
        }
    }

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
            }

            paymentRepository.save(payment);
            orderRepository.save(order);
        }
    }

    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }

    public List<OrderDetail> getOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return orderDetailRepository.findByOrder(order);
    }

    public List<Order> findAllOrdersForAdmin() {
        return orderRepository.findAll();
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
                    Category category = (product != null && !product.getCategories().isEmpty())
                            ? product.getCategories().get(0) : null;

                    Date createdDate = order.getCreatedDate();
                    if (order == null || product == null || category == null || createdDate == null) {
                        return null;
                    }
                    int categoryId = category.getCategoryId();
                    int warrantyMonths = WARRANTY_MONTHS_BY_CATEGORY.getOrDefault(categoryId, 0);

                    LocalDate orderLocalDate = new java.util.Date(createdDate.getTime())
                            .toInstant()
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
    public String processShippingStatusUpdate(long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElse(null);

        if (order == null) {
            return "Order not found.";
        }

        if (order.getStatus().equals(newStatus)) {
            return "No change detected for Order #" + orderId;
        }

        updateShippingStatus(orderId, newStatus);
        return "Success: Order #" + orderId + " updated to " + newStatus;
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
            throw new IllegalArgumentException("Invalid transition from " + currentStatus + " to " + newStatus);
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}