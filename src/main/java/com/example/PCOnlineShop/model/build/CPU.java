package com.example.PCOnlineShop.model.build;

import com.example.PCOnlineShop.model.product.Product;
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
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "socket")
    private String socket; // e.g., LGA1200, AM4
    @Column(name = "tdp")
    private Integer tdp; // in Watts
    @Column(name = "max_memory_speed")
    private Integer maxMemorySpeed; // in GB
    @Column(name = "memory_channels")
    private Integer memoryChannels; // e.g., Dual, Quad
    @Column(name = "has_igpu")
    private Boolean hasIGPU; // Integrated GPU presence
    @Column(name = "pcie_version")
    private String pcieVersion; // e.g., PCIe 4.0

     @OneToOne
     @MapsId
     @JoinColumn( name = "product_id" )
     private Product product;
}
