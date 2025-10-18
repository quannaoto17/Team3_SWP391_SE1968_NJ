package com.example.PCOnlineShop.repository.order;

import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    // Lấy chi tiết theo Order (dùng cho trang chi tiết đơn hàng thông thường)
    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.product p WHERE od.order = :order")
    List<OrderDetail> findByOrder(Order order);

    // --- Phương thức cho Warranty Check ---
    // Lấy chi tiết theo Order ID, JOIN FETCH các association cần thiết
    @Query("SELECT od FROM OrderDetail od " +
            "JOIN FETCH od.order o " +
            "JOIN FETCH od.product p " +
            "JOIN FETCH p.category c " +
            "WHERE o.orderId = :orderId")
    List<OrderDetail> findByOrder_OrderIdWithAssociations(int orderId);

    // Lấy chi tiết theo SĐT khách hàng (dùng bởi OrderService cho Warranty)
    @Query("SELECT od FROM OrderDetail od " +
            "JOIN FETCH od.order o " +
            "JOIN FETCH o.account a " + // Cần join Account
            "JOIN FETCH od.product p " +
            "JOIN FETCH p.category c " +
            "WHERE a.phoneNumber = :phoneNumber ORDER BY o.createdDate DESC, p.productName ASC")
    List<OrderDetail> findByOrder_Account_PhoneNumber(String phoneNumber);
}