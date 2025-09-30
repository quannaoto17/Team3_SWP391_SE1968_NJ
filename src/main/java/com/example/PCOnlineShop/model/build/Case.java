package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
import jakarta.persistence.*;
import  lombok.*;

@Entity
@Table(name = "pc_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;
    private String model;
    private String formFactor; // e.g., ATX, Micro-ATX, Mini-ITX
    private String color;
    private int maxGpuLength; // in mm
    private int maxCpuCoolerHeight; // in mm
    private int numOfDriveBays; // total number of drive bays
    private int numOfFrontUsbPorts; // number of front USB ports
    private boolean hasTemperedGlass; // whether

    @OneToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;
}
