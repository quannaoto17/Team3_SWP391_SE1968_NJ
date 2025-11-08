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

    // Component rules
    private ComponentRule planMotherboard;
    private ComponentRule planCpu;
    private ComponentRule planGpu;
    private ComponentRule planRam;
    private ComponentRule planStorage;
    private ComponentRule planPsu;
    private ComponentRule planCase;
    private ComponentRule planCooling;

    // Convenience methods for backward compatibility
    public void setMainboard(ComponentRule rule) { this.planMotherboard = rule; }
    public void setCpu(ComponentRule rule) { this.planCpu = rule; }
    public void setGpu(ComponentRule rule) { this.planGpu = rule; }
    public void setMemory(ComponentRule rule) { this.planRam = rule; }
    public void setStorage(ComponentRule rule) { this.planStorage = rule; }
    public void setPsu(ComponentRule rule) { this.planPsu = rule; }
    public void setCase(ComponentRule rule) { this.planCase = rule; }
    public void setCooling(ComponentRule rule) { this.planCooling = rule; }
}
