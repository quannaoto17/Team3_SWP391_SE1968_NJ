package com.example.PCOnlineShop.dto.build;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDto {
    private Long productId;
    private String productName;
    private Double price;
    private Integer score;  // Performance score (0-100)
    private String category; // "CPU", "GPU", "Mainboard", etc.
}

