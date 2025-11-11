package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Product name is required" )
    @Size(min = 5, max = 100, message = "Product name must be between 5 and 100 characters")
    @Pattern(regexp = "^(?=.{3,100}$)(?!.* {2})(?!.*[_\\-.]{2})[ \\p{L}\\p{M}\\p{N}\\s\\-\\._/+&,:;'\"®™()\\[\\]°%×–—]+(?<![ \\-_/+&,:;'\"\\.])$")
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

    @Column (name = "created_at")
    private Date createAt;

    @Column (name = "inventory_quantity")
    private Integer inventoryQuantity;

    @Column(name = "performance_score")
    @Min(value = 0, message = "Performance score must be at least 0")
    @Max(value = 100, message = "Performance score must be at most 100")
    private Integer performanceScore = 50; // Default score

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;


}
