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
    // == CÁC PHƯƠNG THỨC QUẢN LÝ ĐƠN HÀNG (GIỮ NGUYÊN) ==
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
        // ... (Code cập nhật hàng loạt giữ nguyên như file bạn cung cấp) ...
        if (updates == null || updates.isEmpty()) return;
        List<Integer> orderIds = new ArrayList<>(updates.keySet());
        List<Order> ordersToUpdate = orderRepository.findAllById(orderIds);
        Map<Integer, Order> orderMap = ordersToUpdate.stream().collect(Collectors.toMap(Order::getOrderId, o -> o));
        boolean changed = false;
        for (Map.Entry<Integer, String> entry : updates.entrySet()) {
            Order order = orderMap.get(entry.getKey());
            if (order != null && !order.getStatus().equals(entry.getValue())) {
                order.setStatus(entry.getValue());
                changed = true;
            } else if (order == null) {
                System.err.println("Order not found for update: " + entry.getKey());
            }
        }
        if (changed) orderRepository.saveAll(ordersToUpdate);
    }

    public Page<Order> findPaginated(Pageable pageable, String phoneNumber) {
        // ... (Code lấy danh sách cho staff giữ nguyên như file bạn cung cấp) ...
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

    // =======================================================
    // == LOGIC CHO STAFF TỰ GIAO HÀNG (ĐÃ THÊM) ==
    // =======================================================

    /**
     * Lấy danh sách các đơn hàng được gán cho một nhân viên Staff cụ thể để giao.
     */
    @Transactional(readOnly = true)
    public List<Order> getAssignedOrdersForStaffMember(Account staffAccount) {
        if (staffAccount == null || staffAccount.getRole() != RoleName.Staff /*&& staffAccount.getRole() != RoleName.Admin*/) {
            return Collections.emptyList();
        }
        List<String> relevantStatuses = List.of("Ready to Ship", "Delivering");
        // Gọi phương thức mới trong OrderRepository
        return orderRepository.findByShipperAndStatusIn(staffAccount, relevantStatuses);
    }

    /**
     * Xử lý việc Staff (người được gán giao hàng) cập nhật trạng thái đơn hàng.
     */
    @Transactional
    public void updateOrderStatusByStaffShipper(int orderId, String newStatus, Account staffShipper) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));

        // 1. Kiểm tra quyền
        if (order.getShipper() == null || order.getShipper().getAccountId() != staffShipper.getAccountId()) {
            throw new SecurityException("Nhân viên không được phép cập nhật đơn hàng này.");
        }

        // 2. Kiểm tra logic chuyển đổi trạng thái
        String currentStatus = order.getStatus();
        boolean isValidTransition = false;
        Date now = new Date();

        if ("Ready to Ship".equals(currentStatus) && "Delivering".equals(newStatus)) {
            isValidTransition = true;
            order.setShipmentReceivedDate(now); // Ghi lại thời điểm nhận hàng
        } else if ("Delivering".equals(currentStatus) && ("Completed".equals(newStatus) || "Delivery Failed".equals(newStatus))) {
            isValidTransition = true;
        }

        if (!isValidTransition) {
            throw new IllegalArgumentException("Không thể chuyển từ trạng thái '" + currentStatus + "' sang '" + newStatus + "'.");
        }

        // 3. Cập nhật và lưu
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

}