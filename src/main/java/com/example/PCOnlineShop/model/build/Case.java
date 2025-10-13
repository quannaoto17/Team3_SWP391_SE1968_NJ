package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import jakarta.persistence.*;
import  lombok.*;

@Entity
@Table(name = "pc_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Case {
    @Id
    @Column(name = "product_id")
    private int productId;
    @Column(name = "form_factor")
    private String formFactor; // e.g., ATX, Micro-ATX, Mini
    @Column(name = "gpu_max_length")
    private int gpuMaxLength; // in mm
    @Column(name = "cpu_max_cooler_height")
    private int cpuMaxCoolerHeight; // in mm
    @Column(name = "psu_max_length")
    private int psuMaxLength; // in mm

    @OneToOne
    @MapsId // dùng chung id với Product
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
