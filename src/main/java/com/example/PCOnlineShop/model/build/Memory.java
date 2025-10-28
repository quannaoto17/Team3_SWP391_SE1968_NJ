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
@Table(name = "memory")

public class Memory {
    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "capacity")
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 GB")
    @Max(value = 256, message = "Capacity must be at most 256 GB")
    private int capacity;

    @Column(name = "type")
    @NotBlank(message = "Memory type is required")
    @Size(min = 2, max = 20, message = "Memory type must be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Memory type must not contain special characters")
    private String type;

    @Column(name = "speed")
    @NotNull(message = "Speed is required")
    @Min(value = 800, message = "Speed must be at least 800 MHz")
    @Max(value = 6000, message = "Speed must be at most 6000 MHz")
    private int speed;

    @Column(name = "tdp")
    @NotNull(message = "TDP is required")
    @Min(value = 1, message = "TDP must be at least 1 watt")
    @Max(value = 100, message = "TDP must be at most 100 watts")
    private int tdp;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
