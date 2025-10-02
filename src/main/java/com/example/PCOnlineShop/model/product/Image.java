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
    @Column (name = "ImageID")
    private int imageId;

    @Column (name = "ImageUrl")
    private String imageUrl;

    @Column (name = "CreatedAt")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;
}
