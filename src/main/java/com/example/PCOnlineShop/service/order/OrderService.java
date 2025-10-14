package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AccountRepository accountRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        AccountRepository accountRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.accountRepository = accountRepository;
    }

    // ==================================================
    // 🔹 PHƯƠNG THỨC DÀNH CHO STAFF
    // ==================================================

    /**
     * Lấy danh sách đơn hàng của khách hàng có phân trang cho Staff xem.
     * Hỗ trợ tìm kiếm theo số điện thoại nếu được cung cấp.
     */
    public Page<Order> findPaginated(Pageable pageable, String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return orderRepository.findByRoleAndPhoneNumber(RoleName.Customer, phoneNumber, pageable);
        }
        return orderRepository.findAllByRole(RoleName.Customer, pageable);
    }

    /**
     * Kiểm tra sự tồn tại của một khách hàng dựa trên số điện thoại.
     */
    public boolean customerAccountExistsByPhoneNumber(String phoneNumber) {
        return accountRepository.existsByPhoneNumberAndRole(phoneNumber, RoleName.Customer);
    }

    /**
     * Cập nhật trạng thái của một đơn hàng.
     * @param orderId ID của đơn hàng cần cập nhật.
     * @param newStatus Trạng thái mới.
     */
    public void updateOrderStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

    // ==================================================
    // 🔹 PHƯƠNG THỨC DÀNH CHO CUSTOMER
    // ==================================================

    /**
     * Lấy tất cả các đơn hàng của một tài khoản cụ thể.
     */
    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }


    // ==================================================
    // 🔹 PHƯƠNG THỨC DÙNG CHUNG
    // ==================================================

    /**
     * Lấy thông tin một đơn hàng bằng ID.
     */
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Lấy danh sách chi tiết các sản phẩm trong một đơn hàng.
     */
    public List<OrderDetail> getOrderDetails(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            return orderDetailRepository.findByOrder(order);
        }
        return Collections.emptyList();
    }
}