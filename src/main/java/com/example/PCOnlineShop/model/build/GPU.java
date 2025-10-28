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
@Table(name = "gpu")

public class GPU {
    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "vram")
    @NotNull(message = "VRAM is required")
    @Min(value = 1, message = "VRAM must be at least 1 GB")
    @Max(value = 48, message = "VRAM must be at most 48 GB")
    private int vram;

    @Column(name = "memory_type")
    @NotBlank(message = "Memory type is required")
    @Size(min = 2, max = 20, message = "Memory type must be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Memory type must not contain special characters")
    private String memoryType;

    @Column(name = "tdp")
    @NotNull(message = "TDP is required")
    @Min(value = 10, message = "TDP must be at least 10 Watts")
    @Max(value = 500, message = "TDP must be at most 500 Watts")
    private int tdp;

    @Column(name = "length")
    @NotNull(message = "Length is required")
    @Min(value = 50, message = "Length must be at least 50 mm")
    @Max(value = 400, message = "Length must be at most 400 mm")
    private int length;


    @Column(name = "gpu_interface")
    @NotBlank(message = "GPU interface is required")
    @Size(min = 2, max = 20, message = "GPU interface must be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "GPU interface must not contain special characters")
    private String gpuInterface;

    @Column(name = "pcie_version")
    @NotBlank(message = "PCIe version is required")
    @Size(min = 1, max = 100, message = "PCIe version must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\.\\s-]+$", message = "PCIe version must not contain special characters")
    private String pcieVersion;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
