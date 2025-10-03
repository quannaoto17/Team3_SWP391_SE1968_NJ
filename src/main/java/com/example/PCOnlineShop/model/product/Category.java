package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table (name = "category")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Category {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "Category_id")
    private int categoryId;

    @Column (name = "Category_name")
    private String categoryName;

    @Column (name = "Description")
    private String description;

    @Column (name = "Display_order")
    private int displayOrder;

    @Column (name = "Created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
