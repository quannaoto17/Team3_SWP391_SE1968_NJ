package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.dto.warranty.WarrantyDetailDTO; // Import DTO đúng
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    // Định nghĩa thời hạn bảo hành theo Category ID
    private static final Map<Integer, Integer> WARRANTY_MONTHS_BY_CATEGORY;

    static {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 12); // Mainboard
        map.put(2, 36); // CPU
        map.put(3, 24); // GPU
        map.put(4, 12); // Memory
        map.put(5, 12); // Storage
        map.put(6, 6);  // Case
        map.put(7, 12); // Power Supply
        map.put(8, 12); // Cooling
        map.put(9, 6);  // Fan
        map.put(10, 6); // Other
        WARRANTY_MONTHS_BY_CATEGORY = Collections.unmodifiableMap(map);
    }

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        AccountRepository accountRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    // ==================================================
    // == CÁC PHƯƠNG THỨC QUẢN LÝ ĐƠN HÀNG
    // ==================================================
    @Transactional
    public Order createOrder(Account customerAccount, Map<Integer, Integer> cartItems,
                             String shippingMethod, String note,
                             String shippingFullName, String shippingPhone, String shippingAddress) {

        Order order = new Order();
        order.setAccount(customerAccount);
        order.setCreatedDate(new Date());
        order.setStatus("Pending Payment"); // <-- Trạng thái chờ thanh toán (Hoàn hảo cho PayOS)
        order.setShippingMethod(shippingMethod);
        order.setNote(note);
        order.setShippingFullName(shippingFullName);
        order.setShippingPhone(shippingPhone);
        order.setShippingAddress(shippingAddress);

        List<OrderDetail> orderDetails = new ArrayList<>();
        double calculatedFinalAmount = 0.0;

        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            // ... (Logic lấy product và tạo OrderDetail)
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new EntityNotFoundException("No Existed Product: " + entry.getKey()));
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(entry.getValue());
            detail.setPrice(product.getPrice());
            orderDetails.add(detail);
            calculatedFinalAmount += (product.getPrice() * entry.getValue());
        }

        order.setFinalAmount(calculatedFinalAmount);
        order.setOrderDetails(orderDetails);

        // ✅ LƯU VÀO CSDL
        return orderRepository.save(order);
    }


    // === PHƯƠNG THỨC MỚI ĐỂ CẬP NHẬT 1 ĐƠN HÀNG ===
    @Transactional
    public void updateSingleOrderStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        String oldStatus = order.getStatus();

        // Chỉ cập nhật nếu status thực sự thay đổi
        if (!oldStatus.equals(newStatus)) {
            order.setStatus(newStatus);

            // --- SET readyToShipDate WHEN STATUS CHANGES TO "Ready to Ship" ---
            // (Chúng ta sao chép logic từ hàm updateMultiple...)
            if (!"Ready to Ship".equals(oldStatus) && "Ready to Ship".equals(newStatus)) {
                order.setReadyToShipDate(new Date()); // Set thời gian hiện tại
            }

            orderRepository.save(order);
        }
        // Nếu status không đổi, không làm gì cả
    }


    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Đơn hàng không tồn tại: " + orderId));
        return orderDetailRepository.findByOrder(order);
    }

    public List<Order> findAllOrdersForAdmin() {
        // Gọi repo mới, sắp xếp DESC (newest) theo default
        return orderRepository.findAllByRole(RoleName.Customer);
    }

    // ==================================================
    // == LOGIC CHO TRANG KIỂM TRA BẢO HÀNH
    // ==================================================

    // Lấy danh sách Orders theo SĐT (cho Warranty check)
    public List<Order> getOrdersByPhoneNumberForWarranty(String phoneNumber) {
        // Gọi phương thức mới trong OrderRepository
        return orderRepository.findByAccount_PhoneNumber(phoneNumber);
    }

    // Lấy và tính toán chi tiết bảo hành của một Order ID
    @Transactional(readOnly = true)
    public List<WarrantyDetailDTO> getWarrantyDetailsByOrderId(int orderId) {
        List<OrderDetail> details = orderDetailRepository.findByOrder_OrderIdWithAssociations(orderId);
        LocalDate today = LocalDate.now(); // Lấy ngày hiện tại

        return details.stream()
                .map(detail -> {
                    // ... (Lấy order, product, category, warrantyMonths như cũ)
                    Order order = detail.getOrder();
                    Product product = detail.getProduct();
                    Category category = (product != null) ? product.getCategory() : null;
                    Date createdDate = order.getCreatedDate();
                    if (order == null || product == null || category == null || createdDate == null) {
                        return null;
                    }
                    int categoryId = category.getCategoryId();
                    int warrantyMonths = WARRANTY_MONTHS_BY_CATEGORY.getOrDefault(categoryId, 0);

                    // --- Tính ngày hết hạn ---
                    LocalDate orderLocalDate;
                    if (createdDate instanceof java.sql.Date) {
                        orderLocalDate = ((java.sql.Date) createdDate).toLocalDate();
                    } else {
                        orderLocalDate = createdDate.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    }
                    LocalDate expiryDate = orderLocalDate.plusMonths(warrantyMonths);

                    // ---TÍNH TOÁN TRẠNG THÁI BẢO HÀNH---
                    String warrantyStatus;
                    long daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate);

                    if (daysUntilExpiry < 0) {
                        warrantyStatus = "Expired"; // Đã hết hạn
                    } else if (daysUntilExpiry <= 7) { // Còn 7 ngày (1 tuần) hoặc ít hơn
                        warrantyStatus = "Expiring Soon";
                    } else {
                        warrantyStatus = "Active"; // Còn hạn trên 2 tuần
                    }
                    // ------------------------------------

                    // Trả về DTO với 6 trường
                    return new WarrantyDetailDTO(
                            order.getOrderId(),
                            product.getProductName(),
                            createdDate,
                            warrantyMonths,
                            expiryDate,
                            warrantyStatus // <-- Truyền status đã tính
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // ==================================================
    // == METHODS FOR SHIPPING MANAGEMENT SCREEN ==
    // ==================================================

    /**
     * Get list of orders currently in the shipping queue (Ready to Ship OR Delivering).
     */
    @Transactional(readOnly = true)
    public List<Order> getShippingQueueOrders() { // Renamed method
        // Fetch orders with either status
        List<String> shippingStatuses = List.of("Ready to Ship", "Delivering");
        return orderRepository.findByStatusIn(shippingStatuses); // Pass both statuses
    }

    /**
     * Method for Staff on the Shipping screen to update status.
     * Handles transitions from Ready to Ship and Delivering.
     */
    @Transactional
    public void updateShippingStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));

        String currentStatus = order.getStatus();
        boolean isValidTransition = false;
        Date now = new Date();

        // Allow updates if current status is Ready to Ship or Delivering
        if ("Ready to Ship".equals(currentStatus) && List.of("Delivering", "Completed", "Cancelled").contains(newStatus)) {
            isValidTransition = true;
            if ("Delivering".equals(newStatus)) {
                order.setReadyToShipDate(now); // Set the date when marked as Delivering
            }
        } else if ("Delivering".equals(currentStatus) && List.of("Completed", "Cancelled", "Delivery Failed").contains(newStatus)) { // Added Delivery Failed
            isValidTransition = true;
        }



        if (!isValidTransition) {
            // Or handle silently and just don't update if transition is invalid from this screen
            System.err.println("Attempted invalid status transition from '" + currentStatus + "' to '" + newStatus + "' on shipping screen.");
            return;
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }


}