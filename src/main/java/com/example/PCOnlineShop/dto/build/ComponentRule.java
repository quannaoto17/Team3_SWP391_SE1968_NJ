package com.example.PCOnlineShop.dto.build;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRule {
    private double budgetMax;
    private int scoreMin;
    private int scoreMax;
}
