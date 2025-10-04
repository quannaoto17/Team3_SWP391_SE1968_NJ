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
    @Column (name = "brand_id")
    private int brandId;

    @Column (name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name = "website")
    private String website;

}
