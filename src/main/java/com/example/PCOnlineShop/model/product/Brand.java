package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "brand")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int brandId;
    private String name;
    private String description;
    private String website;

}
