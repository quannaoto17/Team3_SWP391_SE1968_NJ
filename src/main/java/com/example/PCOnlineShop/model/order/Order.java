package com.example.PCOnlineShop.model.order;

import com.example.PCOnlineShop.model.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private String voucherCode;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
}
