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
    @Column (name = "Product_id")
    private int productId;
    @Column (name = "Product_name")
    private String productName;
    @Column (name = "Price")
    private double price;
    @Column (name = "Status")
    private boolean status;
    @Column (name = "Description")
    private String description;
    @Column (name = "Specification")
    private String specification;
    @Column (name = "Created_at")
    private Date createAt;
    @ManyToOne
    @JoinColumn(name = "Category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "Brand_id", nullable = false)
    private Brand brand;

}
