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
@Table(name = "storage")

public class Storage {
    @Id
    @Column(name = "product_id")
    private int productId;


    @Column(name = "capacity")
    @NotBlank(message = "Capacity is required")
    @Size(min = 1, max = 6, message = "Capacity must be between 1 and 6 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Capacity must be a valid number")
    private int capacity;

    @Column(name = "type")
    @NotBlank(message = "Storage type is required")
    @Size(min = 2, max = 20, message = "Storage type must be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Storage type must not contain special characters")
    private String type;

    @Column(name = "interface")
    @NotBlank(message = "Interface type is required")
    @Size(min = 2, max = 20, message = "Interface type must be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Interface type must not contain special characters")
    private String interfaceType;

    @Column(name = "read_speed")
    @NotNull(message = "Read speed is required")
    @Min(value = 1, message = "Read speed must be at least 1 MB/s")
    @Max(value = 10000, message = "Read speed must be at most 10000 MB/s")
    private int readSpeed;

    @Column(name = "write_speed")
    @NotBlank(message = "Write speed is required")
    @Size(min = 1, max = 6, message = "Write speed must be between 1 and 6 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Write speed must be a valid number")
    private int writeSpeed;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
