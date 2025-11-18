package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import jakarta.validation.constraints.*;
import lombok.*;
import  jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "mainboard")

public class Mainboard {
    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "form_factor")
    @NotBlank(message = "Form factor is required")
    @Size(min = 1, max = 100, message = "Form factor must be between 1 and 100 characters")
   // @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Form factor must not contain special characters")
    private String formFactor;

    @Column(name = "chipset")
    @NotBlank(message = "Chipset is required")
    @Size(min = 1, max = 100, message = "Chipset must be between 1 and 100 characters")
   // @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Chipset must not contain special characters")
    private String chipset;

    @Column(name = "socket")
    @NotBlank(message = "Socket type is required")
    @Size(min = 1, max = 100, message = "Socket type must be between 1 and 100 characters")
 //   @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Socket type must not contain special characters")
    private String socket;

    @Column(name = "memory_type")
    @NotBlank(message = "Memory type is required")
    @Size(min = 2, max = 20, message = "Memory type must be between 2 and 20 characters")
   // @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Memory type must not contain special characters")
    private String memoryType;

    @Column(name = "memory_slots")
    @NotNull(message = "Memory slots is required")
    @Min(value = 1, message = "Memory slots must be at least 1")
    @Max(value = 8, message = "Memory slots must be at most 8")
    private int memorySlots;

    @Column(name = "max_memory_speed")
    @NotNull(message = "Max memory speed is required")
    @Min(value = 1, message = "Max memory speed must be at least) 1 MHz")
    @Max(value = 10000, message = "Max memory speed must be at most 10000 MHz")
    private int maxMemorySpeed;

    @Column(name = "pcie_version")
    @NotBlank(message = "PCIe version is required")
    @Size(min = 1, max = 100, message = "PCIe version must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\.\\s-]+$", message = "PCIe version must not contain special characters")
    private String pcieVersion;

    @Column(name = "m2_slots")
    @NotNull(message = "M.2 slots is required")
    @Min(value = 0, message = "M.2 slots must be at least 0")
    @Max(value = 4, message = "M.2 slots must be at most 4")
    private int m2Slots;

    @Column(name = "sata_ports")
    @NotNull(message = "SATA ports is required")
    @Min(value = 0, message = "SATA ports must be at least 0")
    @Max(value = 10, message = "SATA ports must be at most 10")
    private int sataPorts;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public double getPrice() {
        return product != null ? product.getPrice() : 0;
    }
}
