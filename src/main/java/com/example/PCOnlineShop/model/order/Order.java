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

    // Khách hàng đặt
    @ManyToOne(fetch = FetchType.EAGER) // EAGER để dễ lấy thông tin hiển thị
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Đã bỏ totalAmount, discountAmount, voucherCode, customerEmail

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

    // Thông tin vận đơn (Shipper là người dùng)
    @ManyToOne(fetch = FetchType.LAZY) // LAZY vì không phải lúc nào cũng cần
    @JoinColumn(name = "shipper_account_id") // Khóa ngoại tới bảng account
    private Account shipper; // Tài khoản của shipper

    @Column(name = "tracking_number")
    private String trackingNumber;

    // Quan hệ với OrderDetail (Giữ nguyên)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
}