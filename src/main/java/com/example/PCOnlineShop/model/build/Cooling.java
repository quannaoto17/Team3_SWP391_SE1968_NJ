package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cooling")

public class Cooling {
    @Id
    @Column(name = "product_id")
    private int productId;
    @Column(name = "type")
    private String type; // e.g., Air, Liquid
    @Column(name = "fan_size")
    private int fanSize; // in mm
    @Column(name = "radiator_size")
    private String radiatorSize; // e.g., 120mm, 240mm
    @Column(name = "max_tdp")
    private int maxTdp; // in Watts

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;
}
