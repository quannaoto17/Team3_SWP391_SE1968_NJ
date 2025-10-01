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
    private int productID;
    private String name;
    private String socket; // e.g., LGA1200, AM4
    private String formFactor; // e.g., ATX, Micro-ATX, Mini-ITX
    private int numOfMemorySlots; // number of RAM slots
    private String maxMemorySupported; // e.g., 64GB, 128GB
    private String memoryType; // e.g., DDR4, DDR5
    private int numOfPciSlots; // number of PCIe slots
    private int numOfSataPorts; // number of SATA ports
    private int numOfM2Slots; // number of M.2 slots
    private boolean hasWifi; // whether it has built-in WiFi
    private boolean hasBluetooth; // whether it has built-in Bluetooth

    @OneToOne
    @MapsId
    @JoinColumn(name = "productID")
    private Product product;
}
