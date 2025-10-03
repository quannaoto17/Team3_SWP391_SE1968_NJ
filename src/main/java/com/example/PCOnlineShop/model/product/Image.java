package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table (name = "image")
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Getter
    @Setter
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "Image_id")
    private int imageId;

    @Column (name = "Image_url")
    private String imageUrl;

    @Column (name = "Created_at")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_id", nullable = false)
    private Product product;
}
