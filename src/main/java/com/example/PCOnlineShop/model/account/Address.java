package com.example.PCOnlineShop.model.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_address") // <-- Đổi tên bảng
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id") // <-- Khớp tên cột
    private int addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false) // <-- Khớp tên cột
    @JsonBackReference
    private Account account;

    @NotBlank
    @Column(name = "full_name", length = 100) // <-- Đổi tên từ shippingFullName
    private String fullName;

    @NotBlank
    @Column(name = "phone", length = 20) // <-- Đổi tên từ shippingPhone
    private String phone;

    @NotBlank
    @Column(name = "address", length = 255) // <-- Đổi tên từ shippingAddress
    private String address;

    @Column(name = "is_default") // <-- Khớp tên cột
    private boolean isDefault = false; // BIT(1) sẽ map sang boolean
}