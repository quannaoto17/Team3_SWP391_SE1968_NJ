package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.dto.warranty.WarrantyDetailDTO; // Import DTO đúng
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;

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
    private final PaymentRepository paymentRepository;
    private final PayOS payOS;

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
                        ProductRepository productRepository,
                        PaymentRepository paymentRepository, // ✅ Thêm
                        PayOS payOS) { // ✅ Thêm
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository; // ✅ Thêm
        this.payOS = payOS; // ✅ Thêm
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
        order.setStatus("Pending Payment");
        // ... (set các trường shipping...)
        order.setShippingMethod(shippingMethod);
        order.setNote(note);
        order.setShippingFullName(shippingFullName);
        order.setShippingPhone(shippingPhone);
        order.setShippingAddress(shippingAddress);

        List<OrderDetail> orderDetails = new ArrayList<>();
        double calculatedFinalAmount = 0.0;

        // ✅ BẮT ĐẦU KIỂM TRA VÀ TRỪ KHO (MÔ HÌNH 1)
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int productId = entry.getKey();
            int quantityToBuy = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Sản phẩm: " + productId));

            int currentInventory = product.getInventoryQuantity(); //

            // 1. Kiểm tra tồn kho
            if (currentInventory < quantityToBuy) {
                // Ném lỗi để OrderController bắt được
                throw new IllegalStateException("Sản phẩm " + product.getProductName() + " không đủ số lượng trong kho.");
            }

            // 2. TRỪ KHO NGAY LẬP TỨC
            product.setInventoryQuantity(currentInventory - quantityToBuy);
            productRepository.save(product); // Lưu lại thông tin kho mới

            // 3. (Logic tạo OrderDetail giữ nguyên...)
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(quantityToBuy);
            detail.setPrice(product.getPrice());
            orderDetails.add(detail);
            calculatedFinalAmount += (product.getPrice() * quantityToBuy);
        }
        // ✅ KẾT THÚC TRỪ KHO

        order.setFinalAmount(calculatedFinalAmount);
        order.setOrderDetails(orderDetails);

        return orderRepository.save(order);
    }

    /**
     * MỚI: Hàm helper private để cộng trả lại kho
     */

    private void rollBackInventory(Order order) {
        System.out.println("Bắt đầu hoàn kho cho Order ID: " + order.getOrderId());
        // Lấy chi tiết đơn hàng (đảm bảo nó được fetch)
        List<OrderDetail> details = orderDetailRepository.findByOrder(order);

        for (OrderDetail detail : details) {
            Product product = detail.getProduct();
            int quantityToRollback = detail.getQuantity();

            product.setInventoryQuantity(product.getInventoryQuantity() + quantityToRollback);
            productRepository.save(product);
        }
    }

    /**
     * MỚI: Logic HỦY ĐƠN (dành cho Pending Payment)
     * Được gọi bởi Scheduler, PaymentService (webhook fail), PaymentController (user cancel)
     */
    @Transactional
    public void cancelOrderFromPaymentId(long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Payment: " + paymentId));
        Order order = payment.getOrder();

        // Chỉ hủy nếu nó vẫn đang chờ
        if ("Pending Payment".equals(order.getStatus()) && "PENDING".equals(payment.getStatus())) {
            System.out.println("Hủy và Hoàn kho cho Order ID: " + order.getOrderId());

            payment.setStatus("CANCELLED"); // (Hoặc EXPIRED, FAILED tùy)
            order.setStatus("Cancelled");
            order.setPaymentStatus("CANCELLED");

            // 1. HOÀN KHO
            rollBackInventory(order);

            // 2. Hủy link trên PayOS
            try {
                payOS.paymentRequests().cancel(paymentId, "Hủy do hết hạn hoặc người dùng yêu cầu");
            } catch (Exception e) {
                System.err.println("Lỗi khi hủy link PayOS: " + e.getMessage());
            }

            paymentRepository.save(payment);
            orderRepository.save(order);
        } else {
            System.out.println("Bỏ qua hủy Order ID: " + order.getOrderId() + " (Trạng thái không hợp lệ)");
        }
    }

    /**
     * MỚI: Staff đánh dấu "Processing" -> "Ready to Ship"
     */
    @Transactional
    public void markAsReadyToShip(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng: " + orderId));

        if (!"Processing".equals(order.getStatus())) {
            throw new IllegalStateException("Chỉ có thể chuyển đơn 'Processing' sang 'Ready to Ship'.");
        }
        order.setStatus("Ready to Ship");
        order.setReadyToShipDate(new Date());
        orderRepository.save(order);
    }

    /**
     * Cập nhật: Logic HỦY VÀ REFUND (dành cho Processing)
     * Được gọi bởi OrderController (Staff/Customer)
     */
    @Transactional
    public void refundOrderFromOrderId(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng: " + orderId));

        if (!"Processing".equals(order.getStatus())) {
            throw new IllegalStateException("Chỉ có thể hủy và hoàn tiền cho đơn hàng 'Processing'.");
        }

        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy payment cho đơn hàng: " + orderId));

        // 1. Cập nhật trạng thái
        order.setStatus("Cancelled");
        order.setPaymentStatus("REFUND_PENDING");
        payment.setStatus("REFUND_PENDING");

        // 2. HOÀN KHO
        rollBackInventory(order);

        orderRepository.save(order);
        paymentRepository.save(payment);

        // 3. GỌI API PAYOUT (Hoàn tiền)
        System.out.println("Đã đánh dấu đơn " + orderId + " là Cancelled và REFUND_PENDING.");
        System.out.println("BẠN CẦN KÍCH HOẠT LOGIC PAYOUT API (REFUND) TẠI ĐÂY.");
    }


    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }

    public Order getOrderById(long id) { // ✅ Đã đổi sang long
        return orderRepository.findById(id).orElse(null); // ✅ Sẽ gọi findById(Long id)
    }

    public List<OrderDetail> getOrderDetails(long orderId) { // ✅ Đã đổi sang long
        Order order = orderRepository.findById(orderId) // ✅ Sẽ gọi findById(Long id)
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
    public List<WarrantyDetailDTO> getWarrantyDetailsByOrderId(long orderId) { // ✅ Đã đổi sang long
        List<OrderDetail> details = orderDetailRepository.findByOrder_OrderIdWithAssociations(orderId); // ✅ Giả định repo này cũng đã cập nhật
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
                            order.getOrderId(), // ✅ order.getOrderId() giờ sẽ trả về long
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
    public void updateShippingStatus(long orderId, String newStatus) { // ✅ Đã đổi sang long
        Order order = orderRepository.findById(orderId) // ✅ Sẽ gọi findById(Long id)
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