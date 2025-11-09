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
            "gpu", 0.40,
            "cpu", 0.25,
            "memory", 0.10,
            "storage", 0.08,
            "psu", 0.08,
            "mainboard", 0.07,
            "cooling", 0.04,
            "case", 0.03
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
            "gpu", 0.38,
            "cpu", 0.22,
            "memory", 0.12,
            "storage", 0.10,
            "psu", 0.08,
            "mainboard", 0.07,
            "cooling", 0.03,
            "case", 0.03
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
        1800,
        Map.of(
            "cpu", 0.30,
            "memory", 0.20,
            "gpu", 0.25,
            "storage", 0.15,
            "psu", 0.05,
            "mainboard", 0.03,
            "cooling", 0.01,
            "case", 0.01
        ),
        Map.of(
            "cpu_score_min", 85,
            "gpu_score_min", 75,
            "memory_capacity_min", 32,
            "memory_speed_min", 3200,
            "storage_capacity_min", 1000,
            "psu_wattage_min", 750
        )
    ),

    OFFICE(
        "Office/Productivity",
        "For office work, web browsing, light tasks",
        600,
        Map.of(
            "cpu", 0.25,
            "memory", 0.20,
            "storage", 0.20,
            "mainboard", 0.15,
            "psu", 0.10,
            "gpu", 0.05,
            "cooling", 0.03,
            "case", 0.02
        ),
        Map.of(
            "cpu_score_min", 50,
            "memory_capacity_min", 16,
            "memory_speed_min", 2400,
            "storage_capacity_min", 500,
            "psu_wattage_min", 450
        )
    ),

    BUDGET_GAMING(
        "Budget Gaming",
        "For 1080p gaming at medium settings",
        700,
        Map.of(
            "gpu", 0.35,
            "cpu", 0.25,
            "memory", 0.15,
            "storage", 0.10,
            "psu", 0.07,
            "mainboard", 0.05,
            "cooling", 0.02,
            "case", 0.01
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
            "gpu", 0.30,
            "memory", 0.15,
            "storage", 0.12,
            "psu", 0.08,
            "mainboard", 0.03,
            "cooling", 0.01,
            "case", 0.01
        ),
        Map.of(
            "cpu_score_min", 80,
            "gpu_score_min", 75,
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

