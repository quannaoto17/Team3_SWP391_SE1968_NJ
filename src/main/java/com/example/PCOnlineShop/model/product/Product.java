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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;
    private double price;
    private boolean status;
    private String description;
    private String specification;
    private Date createAt;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brandId", nullable = false)
    private Brand brand;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
