package com.example.PCOnlineShop.model.build;
import com.example.PCOnlineShop.model.product.Product;
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
    private int capacity; // in GB
    @Column(name = "type")
    private String type; // e.g., SSD, HDD
    @Column(name = "interface")
    private String interfaceType; // e.g., SATA, NVMe
    @Column(name = "read_speed")
    private int readSpeed; // in MB/s
    @Column(name = "write_speed")
    private int writeSpeed; // in MB/s

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
