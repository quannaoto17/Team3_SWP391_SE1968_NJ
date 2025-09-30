package com.example.PCOnlineShop.model.build;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cpu")              // đúng với tên bảng của bạn
public class CPU {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // Nếu productId do bạn tự set = ProductID bên Product, giữ nguyên KHÔNG @GeneratedValue
    private Integer productId;

    private String name;
    private String brand;
    private String model;
    private Integer cores;
    private Integer threads;
    private Double baseClock;     // GHz
    private Double boostClock;    // GHz
    private String socketType;
    private Integer tdp;          // Watts

    // ❌ Bỏ hoàn toàn quan hệ với Product
    // @OneToOne
    // private Product product;
}
