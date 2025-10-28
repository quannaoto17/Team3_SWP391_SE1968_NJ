package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Form factor is required")
    @Size(min =  1, max = 100, message = "Form factor must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Form factor must not contain special characters")
    private String formFactor;

    @Column(name = "gpu_max_length")
    @NotNull(message = "GPU max length is required")
    @Min(value = 1, message = "GPU max length must be at least 1 mm")
    @Max(value = 500, message = "GPU max length must be at most 500 mm")
    private int gpuMaxLength; // in mm

    @Column(name = "cpu_max_cooler_height")
    @NotNull(message = "CPU max cooler height is required")
    @Min(value = 1, message = "CPU max cooler height must be at least 1 mm")
    @Max(value = 300, message = "CPU max cooler height must be at most 300 mm")
    private int cpuMaxCoolerHeight; // in mm

    @Column(name = "psu_max_length")
    @NotNull(message = "PSU max length is required")
    @Min(value = 1, message = "PSU max length must be at least 1 mm")
    @Max(value = 300, message = "PSU max length must be at most 300 mm")
    private int psuMaxLength; // in mm

    @OneToOne
    @MapsId // dùng chung id với Product
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
