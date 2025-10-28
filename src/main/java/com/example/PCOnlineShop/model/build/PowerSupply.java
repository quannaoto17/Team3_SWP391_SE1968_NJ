package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "Wattage is required")
    @Min(value = 100, message = "Wattage must be at least 100 watts")
    @Max(value = 2000, message = "Wattage must be at most 2000 watts")
    private int wattage;

    @Column(name = "efficiency")
    @NotBlank(message = "Efficiency rating is required")
    @Size(min = 2, max = 10, message = "Efficiency rating must be between 2 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Efficiency rating must not contain special characters")
    private String efficiency;

    @Column(name = "modular")
    private boolean modular;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
