package com.example.PCOnlineShop.repository.order;

import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // ✅ Thêm import này

import java.util.List;

// JpaRepository<OrderDetail, Integer> là ĐÚNG, vì PK của OrderDetail (order_detail_id) là int
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    // Lấy chi tiết theo Order
    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.product p WHERE od.order = :order")
    List<OrderDetail> findByOrder(@Param("order") Order order);

    // --- Phương thức cho Warranty Check ---
    // Lấy chi tiết theo Order ID, JOIN FETCH các association cần thiết
    @Query("SELECT od FROM OrderDetail od " +
            "JOIN FETCH od.order o " +
            "JOIN FETCH od.product p " +
            "JOIN FETCH p.category c " +
            "WHERE o.orderId = :orderId")
    // ✅ SỬA LỖI: Thay int orderId thành long orderId
    List<OrderDetail> findByOrder_OrderIdWithAssociations(@Param("orderId") long orderId);

    //  Kiểm tra xem account đã mua sản phẩm cụ thể hay chưa (và đơn hàng đã hoàn tất)
    // (Giả sử accountId và productId vẫn là Integer)
    boolean existsByOrder_Account_AccountIdAndProduct_ProductIdAndOrder_Status(
            Integer accountId, Integer productId, String status);
}