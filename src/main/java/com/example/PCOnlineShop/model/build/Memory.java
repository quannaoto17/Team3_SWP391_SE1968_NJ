package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "memory")

public class Memory {
    @Id
    @Column(name = "product_id")
    private int productId;
    @Column(name = "capacity")
    private int capacity; // in GB
    @Column(name = "type")
    private String type; // e.g., DDR4, DDR5
    @Column(name = "speed")
    private int speed; // in MHz
    @Column(name = "tdp")
    private int tdp; // in Watts
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
