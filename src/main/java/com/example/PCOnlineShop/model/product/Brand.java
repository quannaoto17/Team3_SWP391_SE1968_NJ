package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "brand")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Brand {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "BrandID")
    private int brandId;

    @Column (name = "Name")
    private String name;

    @Column (name = "Description")
    private String description;

    @Column (name = "Website")
    private String website;

}
