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
@Table(name = "cooling")

public class Cooling {
    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "type")
    @NotBlank(message = "Cooling type is required")
    @Size(min = 1, max = 100, message = "Cooling type must be between 1 and 100 characters")
    //@Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Cooling type must not contain special characters")
    private String type; // e.g., Air, Liquid

    @Column(name = "fan_size")
    @NotNull(message = "Fan size is required")
    @Min(value = 40, message = "Fan size must be at least 40mm")
    @Max(value = 200, message = "Fan size must be at most 200mm")
    private int fanSize; // in mm

    @Column(name = "radiator_size")
    @Min(value = 0, message = "Radiator size must be at least 0mm")
    @Max(value = 480, message = "Radiator size must be at most 480mm")
    private int radiatorSize; // in mm (e.g., 120, 240, 280, 360, 480)

    @Column(name = "max_tdp")
    @NotNull(message = "Max TDP is required")
    @Min(value = 1, message = "Max TDP must be at least")
    @Max(value = 500, message = "Max TDP must be at most 500 Watts")
    private int tdp; // in Watts

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
