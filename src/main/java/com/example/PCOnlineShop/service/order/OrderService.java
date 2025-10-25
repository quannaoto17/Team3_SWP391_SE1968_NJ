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
        // ... (Code tạo đơn hàng giữ nguyên như file bạn cung cấp) ...
        Order order = new Order();
        order.setAccount(customerAccount);
        order.setCreatedDate(new Date());
        order.setStatus("Pending Payment");
        order.setShippingMethod(shippingMethod);
        order.setNote(note);
        order.setShippingFullName(shippingFullName);
        order.setShippingPhone(shippingPhone);
        order.setShippingAddress(shippingAddress);
        List<OrderDetail> orderDetails = new ArrayList<>();
        double calculatedFinalAmount = 0.0;
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("No Existed Product: " + productId));
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(quantity);
            detail.setPrice(product.getPrice());
            orderDetails.add(detail);
            calculatedFinalAmount += (product.getPrice() * quantity);
        }
        order.setFinalAmount(calculatedFinalAmount);
        order.setOrderDetails(orderDetails);
        return orderRepository.save(order);
    }


    @Transactional
    public void updateMultipleOrderStatuses(Map<Integer, String> updates) {
        if (updates == null || updates.isEmpty()) return;

        List<Integer> orderIds = new ArrayList<>(updates.keySet());
        List<Order> ordersToUpdate = orderRepository.findAllById(orderIds);
        Map<Integer, Order> orderMap = ordersToUpdate.stream().collect(Collectors.toMap(Order::getOrderId, o -> o));
        boolean changed = false;
        Date now = new Date(); // Get current time once

        for (Map.Entry<Integer, String> entry : updates.entrySet()) {
            Order order = orderMap.get(entry.getKey());
            String newStatus = entry.getValue();

            if (order != null && !order.getStatus().equals(newStatus)) {
                String oldStatus = order.getStatus(); // Store old status
                order.setStatus(newStatus);
                changed = true;

                // --- SET readyToShipDate WHEN STATUS CHANGES TO "Ready to Ship" ---
                if (!"Ready to Ship".equals(oldStatus) && "Ready to Ship".equals(newStatus)) {
                    order.setReadyToShipDate(now);
                }
                // --- Keep other status change logic if needed ---

            } else if (order == null) {
                System.err.println("Order not found for update: " + entry.getKey());
            }
        }

        if (changed) {
            orderRepository.saveAll(ordersToUpdate);
        }
    }

    public Page<Order> findPaginated(Pageable pageable, String phoneNumber) {

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return orderRepository.findByRoleAndPhoneNumber(RoleName.Customer, phoneNumber, pageable);
        }
        return orderRepository.findAllByRole(RoleName.Customer, pageable);
    }

    public boolean customerAccountExistsByPhoneNumber(String phoneNumber) {
        // ... (Code kiểm tra customer tồn tại giữ nguyên như file bạn cung cấp) ...
        return accountRepository.existsByPhoneNumberAndRole(phoneNumber, RoleName.Customer);
    }

    public List<Order> getOrdersByAccount(Account account) {
        // ... (Code lấy đơn hàng cho customer giữ nguyên như file bạn cung cấp) ...
        return orderRepository.findByAccount(account);
    }

    public Order getOrderById(int id) {
        // ... (Code lấy đơn hàng theo ID giữ nguyên như file bạn cung cấp) ...
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        // ... (Code lấy chi tiết đơn hàng giữ nguyên như file bạn cung cấp) ...
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Đơn hàng không tồn tại: " + orderId));
        return orderDetailRepository.findByOrder(order);
    }

    // ==================================================
    // == LOGIC CHO TRANG KIỂM TRA BẢO HÀNH (ĐÃ THÊM) ==
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

                    // --- Tính ngày hết hạn (giữ nguyên) ---
                    LocalDate orderLocalDate;
                    if (createdDate instanceof java.sql.Date) {
                        orderLocalDate = ((java.sql.Date) createdDate).toLocalDate();
                    } else {
                        orderLocalDate = createdDate.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    }
                    LocalDate expiryDate = orderLocalDate.plusMonths(warrantyMonths);

                    // --- 👇 TÍNH TOÁN TRẠNG THÁI BẢO HÀNH 👇 ---
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
        // Use the existing repository method, just change the statuses passed in
        // Assuming findByStatusWithAccount fetches the necessary associations (like Account)
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
        // Optional: Allow changing back from Delivering to Ready to Ship?
         /* else if ("Delivering".equals(currentStatus) && "Ready to Ship".equals(newStatus)) {
              isValidTransition = true;
              order.setReadyToShipDate(null); // Clear the date if moved back
         } */


        if (!isValidTransition) {
            // Or handle silently and just don't update if transition is invalid from this screen
            System.err.println("Attempted invalid status transition from '" + currentStatus + "' to '" + newStatus + "' on shipping screen.");
            // Optional: throw new IllegalStateException("Cannot change status from " + currentStatus + " to "+ newStatus + " on this screen.");
            return; // Don't update if invalid
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}