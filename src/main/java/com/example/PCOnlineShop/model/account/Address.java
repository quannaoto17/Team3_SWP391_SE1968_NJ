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
    @Column(name = "address_id")
    private int addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @NotBlank
    @Column(name = "full_name", length = 100)
    private String fullName;

    @NotBlank
    @Column(name = "phone", length = 20)
    private String phone;

    @NotBlank
    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "is_default")
    private boolean isDefault = false;
}