package com.example.PCOnlineShop.model.payment;

import com.example.PCOnlineShop.model.order.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payments") //
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id") //
    private long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false) //
    private Order order;

    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private String status;

    @Column(name = "raw_payload", columnDefinition = "TEXT") //
    private String rawPayload; // Lưu trữ JSON webhook

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false) //
    private Date createdAt;

    @Column(name = "order_code", unique = true) //
    private Long orderCode;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}