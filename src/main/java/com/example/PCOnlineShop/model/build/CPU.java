package com.example.PCOnlineShop.model.build;

import com.example.PCOnlineShop.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Socket type is required")
    @Size(min = 1, max = 100, message = "Socket type must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Socket type must not contain special characters")
    private String socket;

    @Column(name = "tdp")
    @NotNull(message = "TDP is required")
    @Min(value = 1, message = "TDP must be at least 1 watt")
    @Max(value = 500, message = "TDP must be at most 500 watts")
    private Integer tdp;

    @Column(name = "max_memory_speed")
    @NotNull(message = "Max memory speed is required")
    @Min(value = 1, message = "Max memory speed must be at least 1 MHz")
    @Max(value = 10000, message = "Max memory speed must be at most 10000 MHz")
    private Integer maxMemorySpeed;

    @Column(name = "memory_channels")
    @NotNull(message = "Memory channels is required")
    @Min(value = 1, message = "Memory channels must be at least 1")
    @Max(value = 8, message = "Memory channels must be at most 8")
    private Integer memoryChannels;

    @Column(name = "has_igpu")
    private Boolean hasIGPU;

    @Column(name = "pcie_version")
    @NotBlank(message = "PCIe version is required")
    @Size(min = 1, max = 100, message = "PCIe version must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\.\\s-]+$", message = "PCIe version must not contain special characters")
    private String pcieVersion;

     @OneToOne(fetch = FetchType.EAGER)
     @MapsId
     @JoinColumn( name = "product_id" )
     private Product product;

     public double getPrice() {
         return product != null ? product.getPrice() : 0;
     }
}
