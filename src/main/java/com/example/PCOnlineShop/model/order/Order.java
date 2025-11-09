package com.example.PCOnlineShop.model.order;

import com.example.PCOnlineShop.model.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @Column(name = "order_id")
    private long orderId;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER để dễ lấy thông tin hiển thị
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "final_amount")
    private Double finalAmount; // Tổng tiền cuối cùng

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    // Thông tin từ checkout
    @Column(name = "shipping_method", nullable = false)
    private String shippingMethod;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    // Thông tin địa chỉ giao hàng (snapshot)
    @Column(name = "shipping_full_name", nullable = false)
    private String shippingFullName;

    @Column(name = "shipping_phone", nullable = false)
    private String shippingPhone;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ready_to_ship_date")
    private Date readyToShipDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shipment_received_date")
    private Date shipmentReceivedDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}