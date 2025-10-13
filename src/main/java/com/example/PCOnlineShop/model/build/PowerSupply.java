package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "power_supply")

public class PowerSupply {
    @Id
    @Column(name = "product_id")
    private int productId;
    @Column(name = "wattage")
    private int wattage; // in Watts
    @Column(name = "efficiency")
    private String efficiency; // e.g., 80 Plus Bronze, Gold
    @Column(name = "modular")
    private boolean modular; // true if modular, false otherwise

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
