package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ProductID")
    private int productId;
    @Column (name = "ProductName")
    private String productName;
    @Column (name = "Price")
    private double price;
    @Column (name = "Status")
    private boolean status;
    @Column (name = "Description")
    private String description;
    @Column (name = "Specification")
    private String specification;
    @Column (name = "CreatedAt")
    private Date createAt;
    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "BrandID", nullable = false)
    private Brand brand;

}
