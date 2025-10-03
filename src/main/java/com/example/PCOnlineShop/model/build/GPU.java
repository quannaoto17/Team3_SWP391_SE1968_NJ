package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "gpu")

public class GPU {
    @Id
    @Column(name = "product_id")
    private int productId;
    @Column(name = "vram")
    private int vram; // in GB
    @Column(name = "memory_type")
    private String memoryType; // e.g., GDDR6, HBM2
    @Column(name = "tdp")
    private int tdp; // in Watts
    @Column(name = "length")
    private int length; // in mm
    @Column(name = "gpu_interface")
    private String gpuInterface; // e.g., PCIe 4.0
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;
}
