package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
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
    private String formFactor; // e.g., ATX, Micro-ATX, Mini
    @Column(name = "chipset")
    private String chipset; // e.g., B550, Z490
    @Column(name = "socket")
    private String socket; // e.g., AM4, LGA1200
    @Column(name = "memory_type")
    private String memoryType; // e.g., DDR4, DDR5
    @Column(name = "memory_slots")
    private int memorySlots; // number of RAM slots
    @Column(name = "max_memory_speed")
    private int maxMemorySpeed; // in MHz
    @Column(name = "pcie_version")
    private String pcieVersion; // e.g., PCIe 4.0, PCIe 3.0
    @Column(name = "m2_slots")
    private int m2Slots; // number of M.2 slots
    @Column(name = "sata_ports")
    private int sataPorts; // number of SATA portsq

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;
}
