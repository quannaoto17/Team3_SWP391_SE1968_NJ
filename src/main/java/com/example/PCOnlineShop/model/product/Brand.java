package com.example.PCOnlineShop.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private Integer brandId;

    @Column (name = "name")
    @NotBlank(message = "Brand's name must not be null")
    @Size(min = 1, max = 100, message = "Brand's name should be around 1 to 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Must not contain special characters")
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name = "website")
    private String website;

    @Column(name = "status")
    private Boolean status;
}
