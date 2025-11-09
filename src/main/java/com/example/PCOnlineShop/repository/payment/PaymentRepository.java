package com.example.PCOnlineShop.repository.payment;

import com.example.PCOnlineShop.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * Tìm các thanh toán (payments) ở trạng thái PENDING
     * và được tạo (created_at) trước thời điểm timeLimit.
     */
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.createdAt < :timeLimit")
    List<Payment> findPendingPaymentsOlderThan(@Param("timeLimit") LocalDateTime timeLimit);

     Optional<Payment> findByOrder_OrderId(long orderId);
}