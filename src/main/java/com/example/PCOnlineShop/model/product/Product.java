package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @NotBlank(message = "Product name is required" )
    @Size(min = 5, max = 100, message = "Product name must be between 5 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Product name must not contain special characters")
    @Column (name = "product_name")
    private String productName;


    @Column (name = "price")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "100000.00", message = "Price must be at most 100000.00")
    private double price;


    @Column (name = "status")
    private boolean status;

    @Column (name = "description")
    private String description;

    @Column (name = "specification")
    private String specification;

    @Column (name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createAt;

    @Column (name = "inventory_quantity")
    @Min(value = 0, message = "Inventory must be positive")
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
