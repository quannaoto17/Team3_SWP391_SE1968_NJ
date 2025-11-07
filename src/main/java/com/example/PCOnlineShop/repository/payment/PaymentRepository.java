package com.example.PCOnlineShop.repository.payment;

import com.example.PCOnlineShop.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);
    Optional<Payment> findByOrderId(Integer orderId);
}
