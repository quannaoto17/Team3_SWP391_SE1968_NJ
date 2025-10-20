package com.example.PCOnlineShop.model.feedback;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "feedback")
@Getter @Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 500)
    private String comment;

    // 1..5
    private Integer rating;

    // "Allow" | "Denied"
    @Column(name = "comment_status", length = 50)
    private String commentStatus;

    @Column(length = 500)
    private String reply;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
