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
    @Column (name = "product_id")
    private int productId;
    @Column (name = "product_name")
    private String productName;
    @Column (name = "price")
    private double price;
    @Column (name = "status")
    private boolean status;
    @Column (name = "description")
    private String description;
    @Column (name = "specification")
    private String specification;
    @Column (name = "created_at")
    private Date createAt;
    @Column (name = "inventory_quantity")
    private Integer inventoryQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;


}
