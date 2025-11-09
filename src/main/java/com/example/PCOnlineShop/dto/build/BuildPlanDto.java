package com.example.PCOnlineShop.dto.build;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildPlanDto {
    // Build metadata
    private double totalBudget;
    private String purpose;  // e.g., "Gaming - High End"

    // Selected components (actual products)
    private ComponentDto mainboard;
    private ComponentDto cpu;
    private ComponentDto gpu;
    private ComponentDto memory;
    private ComponentDto storage;
    private ComponentDto powerSupply;
    private ComponentDto pcCase;
    private ComponentDto cooling;
}
