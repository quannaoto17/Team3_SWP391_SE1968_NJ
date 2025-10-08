package com.example.PCOnlineShop.repository.warranty;

import com.example.PCOnlineShop.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantyRepository extends JpaRepository<Order, Integer> {

    @Query("""
        SELECT o FROM Order o
        JOIN o.account a
        WHERE a.phoneNumber = :phone
    """)
    List<Order> findOrdersByPhone(@Param("phone") String phone);

    @Query(value = """
        SELECT p.product_name AS productName,
               od.quantity AS quantity,
               p.warranty_period AS warrantyPeriod,
               DATE_ADD(o.created_date, INTERVAL p.warranty_period MONTH) AS expiryDate
        FROM order_detail od
        JOIN product p ON od.product_id = p.product_id
        JOIN orders o ON o.order_id = od.order_id
        WHERE o.order_id = :orderId
    """, nativeQuery = true)
    List<Object[]> findWarrantyByOrderId(@Param("orderId") int orderId);
}
