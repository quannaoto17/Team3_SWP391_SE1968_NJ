package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

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
    // 🔹 TẠO ĐƠN HÀNG MỚI (TỪ CHECKOUT) 🔹
    // ==================================================
    @Transactional
    public Order createOrder(Account customerAccount, Map<Integer, Integer> cartItems,
                             String shippingMethod, String note,
                             String shippingFullName, String shippingPhone, String shippingAddress) {

        Order order = new Order();
        order.setAccount(customerAccount);
        order.setCreatedDate(new Date());
        order.setStatus("Pending Payment"); // Trạng thái ban đầu

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
                    .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại: " + productId));

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

    // ==================================================
    // 🔹 CẬP NHẬT TRẠNG THÁI & GÁN SHIPPER (CHO STAFF) 🔹
    // ==================================================
    @Transactional
    public void updateOrderShipping(int orderId, String newStatus, Integer shipperAccountId, String trackingNum) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Đơn hàng không tồn tại: " + orderId));

        order.setStatus(newStatus);
        order.setTrackingNumber(trackingNum);

        if (shipperAccountId != null) {
            Account shipper = accountRepository.findById(shipperAccountId)
                    .orElseThrow(() -> new EntityNotFoundException("Shipper không tồn tại: " + shipperAccountId));
            if (!shipper.getRole().equals(RoleName.Shipper)) {
                throw new IllegalArgumentException("Tài khoản được gán không phải là Shipper.");
            }
            order.setShipper(shipper);
        } else {
            order.setShipper(null);
        }

        orderRepository.save(order);
    }

    // ==================================================
    // 🔹 CẬP NHẬT HÀNG LOẠT TRẠNG THÁI (CHO STAFF - FORM TỔNG) 🔹
    // ==================================================
    @Transactional
    public void updateMultipleOrderStatuses(Map<Integer, String> updates) {
        if (updates == null || updates.isEmpty()) {
            return;
        }

        List<Integer> orderIds = new ArrayList<>(updates.keySet());
        List<Order> ordersToUpdate = orderRepository.findAllById(orderIds);

        Map<Integer, Order> orderMap = ordersToUpdate.stream()
                .collect(Collectors.toMap(Order::getOrderId, order -> order));

        boolean changed = false;

        for (Map.Entry<Integer, String> entry : updates.entrySet()) {
            Integer orderId = entry.getKey();
            String newStatus = entry.getValue();
            Order order = orderMap.get(orderId);

            if (order != null && !order.getStatus().equals(newStatus)) {
                order.setStatus(newStatus);
                changed = true;
                // Thêm logic khác nếu cần (ghi log, etc.)
            } else if (order == null) {
                System.err.println("Order not found for update: " + orderId);
            }
        }

        if (changed) {
            orderRepository.saveAll(ordersToUpdate);
        }
    }

    // ==================================================
    // 🔹 LẤY DỮ LIỆU ĐƠN HÀNG (CHO STAFF & CUSTOMER) 🔹
    // ==================================================

    // Lấy danh sách cho Staff (phân trang, tìm kiếm)
    public Page<Order> findPaginated(Pageable pageable, String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            // Đảm bảo JOIN FETCH account trong repository
            return orderRepository.findByRoleAndPhoneNumber(RoleName.Customer, phoneNumber, pageable);
        }
        // Đảm bảo JOIN FETCH account trong repository
        return orderRepository.findAllByRole(RoleName.Customer, pageable);
    }

    // Kiểm tra khách hàng tồn tại
    public boolean customerAccountExistsByPhoneNumber(String phoneNumber) {
        return accountRepository.existsByPhoneNumberAndRole(phoneNumber, RoleName.Customer);
    }

    // Lấy danh sách cho Customer
    public List<Order> getOrdersByAccount(Account account) {
        // Cần đảm bảo repository có JOIN FETCH nếu cần tối ưu
        return orderRepository.findByAccount(account);
    }

    // Lấy đơn hàng theo ID (dùng chung)
    public Order getOrderById(int id) {
        // Nên JOIN FETCH account, shipper nếu cần hiển thị tên ở trang detail
        return orderRepository.findById(id).orElse(null);
    }

    // Lấy chi tiết đơn hàng (dùng chung)
    public List<OrderDetail> getOrderDetails(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Đơn hàng không tồn tại: " + orderId));
        // Nên JOIN FETCH product trong repository để lấy tên sản phẩm
        return orderDetailRepository.findByOrder(order);
    }
}