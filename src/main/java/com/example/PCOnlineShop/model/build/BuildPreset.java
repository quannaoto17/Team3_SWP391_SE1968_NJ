package com.example.PCOnlineShop.model.build;

import lombok.Getter;
import java.util.Map;

@Getter
public enum BuildPreset {
    GAMING_HIGH(
        "Gaming - High End",
        "For AAA gaming at 1440p/4K with max settings",
        2000,
        Map.of(
            "gpu", 0.36,        // $740
            "cpu", 0.23,        // $500
            "memory", 0.10,     // $200
            "storage", 0.08,    // $160
            "psu", 0.08,        // $160
            "mainboard", 0.1,  // $140
            "case", 0.05,       // $100 (was $80) - INCREASED for better case
            "cooling", 0.04     // $80
        ),
        Map.of(
            "gpu_score_min", 85,
            "cpu_score_min", 80,
            "memory_capacity_min", 16,
            "memory_speed_min", 3200,
            "storage_capacity_min", 1000,
            "psu_wattage_min", 750
        )
    ),

    GAMING_MID(
        "Gaming - Mid Range",
        "For 1080p/1440p gaming at high settings",
        1200,
        Map.of(
            "gpu", 0.35,        // $420
            "cpu", 0.22,        // $264
            "memory", 0.12,     // $144
            "storage", 0.10,    // $120
            "psu", 0.08,        // $96
            "mainboard", 0.07,  // $84
            "case", 0.06,       // $72 (was $48) - INCREASED for better case
            "cooling", 0.03     // $36
        ),
        Map.of(
            "gpu_score_min", 70,
            "cpu_score_min", 70,
            "memory_capacity_min", 16,
            "memory_speed_min", 3200,
            "storage_capacity_min", 500,
            "psu_wattage_min", 650
        )
    ),

    WORKSTATION(
        "Workstation",
        "For 3D rendering, video editing, CAD",
        1400,                   // LOWERED: More realistic min budget
        Map.of(
            "cpu", 0.30,        // $420
            "memory", 0.20,     // $280
            "gpu", 0.25,        // $350
            "storage", 0.12,    // $168
            "mainboard", 0.06,  // $84
            "psu", 0.05,        // $70
            "case", 0.03,       // $42
            "cooling", 0.02     // $28
        ),
        Map.of(
            "cpu_score_min", 75,                // LOWERED: i5/Ryzen 5 level is OK
            "gpu_score_min", 65,                // LOWERED: RTX 3060/RX 6600 XT level
            "memory_capacity_min", 16,          // LOWERED: 16GB is fine for entry workstation
            "memory_speed_min", 3200,           // OK
            "storage_capacity_min", 500,        // LOWERED: 500GB is OK for start
            "psu_wattage_min", 650              // LOWERED: 650W is enough
        )
    ),

    OFFICE(
        "Office/Productivity",
        "For office work, web browsing, light tasks",
        600,
        Map.of(
            "cpu", 0.25,        // $150
            "memory", 0.20,     // $120
            "storage", 0.20,    // $120
            "mainboard", 0.15,  // $90
            "psu", 0.10,        // $60
            "gpu", 0.05,        // $30 (optional, iGPU is fine)
            "case", 0.05,       // $30
            "cooling", 0.03     // $18
        ),
        Map.of(
            "cpu_score_min", 40,                // LOWERED: Pentium/i3/Ryzen 3 level
            "memory_capacity_min", 8,           // LOWERED: 8GB is enough for office
            "memory_speed_min", 2400,           // OK
            "storage_capacity_min", 240,        // LOWERED: 240GB SSD is fine
            "psu_wattage_min", 450              // OK
        )
    ),

    BUDGET_GAMING(
        "Budget Gaming",
        "For 1080p gaming at medium settings",
        700,
        Map.of(
            "gpu", 0.32,        // $224
            "cpu", 0.25,        // $175
            "memory", 0.15,     // $105
            "storage", 0.10,    // $70
            "psu", 0.07,        // $49
            "mainboard", 0.05,  // $35
            "case", 0.04,       // $28 (was $14) - INCREASED for GPU fit
            "cooling", 0.02     // $14
        ),
        Map.of(
            "gpu_score_min", 50,
            "cpu_score_min", 50,
            "memory_capacity_min", 8,
            "memory_speed_min", 2400,
            "storage_capacity_min", 500,
            "psu_wattage_min", 500
        )
    ),

    STREAMING(
        "Streaming/Content Creation",
        "For gaming + streaming + video editing",
        1800,
        Map.of(
            "cpu", 0.30,
            "gpu", 0.28,
            "memory", 0.15,
            "storage", 0.12,
            "psu", 0.08,
            "case", 0.04,       // $72 (was $18) - INCREASED
            "mainboard", 0.03,
            "cooling", 0.01
        ),
        Map.of(
            "cpu_score_min", 80,
            "gpu_score_min", 70,
            "memory_capacity_min", 32,
            "memory_speed_min", 3200,
            "storage_capacity_min", 1000,
            "psu_wattage_min", 750
        )
    );

    private final String name;
    private final String description;
    private final double suggestedMinBudget;
    private final Map<String, Double> budgetDistribution;
    private final Map<String, Integer> requirements;

    BuildPreset(String name, String description, double suggestedMinBudget,
                Map<String, Double> budgetDistribution,
                Map<String, Integer> requirements) {
        this.name = name;
        this.description = description;
        this.suggestedMinBudget = suggestedMinBudget;
        this.budgetDistribution = budgetDistribution;
        this.requirements = requirements;
    }

    public double calculateComponentBudget(String component, double totalBudget) {
        return totalBudget * budgetDistribution.getOrDefault(component, 0.0);
    }

    public int getRequirement(String key) {
        return requirements.getOrDefault(key, 0);
    }
}

