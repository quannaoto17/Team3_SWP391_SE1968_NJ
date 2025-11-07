package com.example.PCOnlineShop.model.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Integer orderId;

    @Column(length = 255)
    private String gatewayPaymentId;

    @Column(precision = 10)
    private Double amount;

    @Column(length = 10)
    private String currency = "VND";

    @Column(length = 50)
    private String status; // PENDING, SUCCESS, FAILED...

    @Lob
    private String rawPayload;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}