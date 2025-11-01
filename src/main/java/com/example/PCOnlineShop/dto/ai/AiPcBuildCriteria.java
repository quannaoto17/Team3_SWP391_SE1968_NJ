package com.example.PCOnlineShop.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing AI analysis criteria for PC build
 * Đây là phôi - sẽ được mở rộng sau
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiPcBuildCriteria {
    // Component performance levels
    private String gpuPerformanceLevel;   // "high", "medium", "low"
    private String cpuPerformanceLevel;   // "high", "medium", "low"

    // Memory requirements
    private Integer minRamCapacity;       // in GB
    private String ramType;               // "DDR4", "DDR5"

    // Storage requirements
    private Integer minStorageCapacity;   // in GB
    private String storageType;           // "SSD", "NVMe"

    // Budget allocation (in million VND)
    private Double gpuBudget;
    private Double cpuBudget;
    private Double mainboardBudget;
    private Double ramBudget;
    private Double storageBudget;
    private Double psuBudget;
    private Double caseBudget;
    private Double coolingBudget;

    // Additional info
    private String useCase;               // "gaming", "workstation", "office"
    private String additionalNotes;
}

