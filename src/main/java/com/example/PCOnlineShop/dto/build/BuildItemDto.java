package com.example.PCOnlineShop.dto.build;

import com.example.PCOnlineShop.model.build.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildItemDto {
    private Mainboard mainboard;
    private CPU cpu;
    private Memory memory;
    private GPU gpu;
    private Storage storage;
    private PowerSupply powerSupply;
    private Case pcCase;
    private Cooling cooling;

    public boolean isEmpty() {
        return mainboard == null && cpu == null && memory == null && gpu == null &&
               storage == null && powerSupply == null && pcCase == null &&
               cooling == null;
    }

    public double calculateTotalPrice() {
        double total = 0;
        if (mainboard != null) total += mainboard.getPrice();
        if (cpu != null) total += cpu.getPrice();
        if (memory != null) total += memory.getPrice();
        if (gpu != null) total += gpu.getPrice();
        if (storage != null) total += storage.getPrice();
        if (powerSupply != null) total += powerSupply.getPrice();
        if (pcCase != null) total += pcCase.getPrice();
        if (cooling != null) total += cooling.getPrice();
        // Round to 2 decimal places
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Get formatted total price string with 2 decimal places
     */
    public String getFormattedTotalPrice() {
        return String.format("%.2f", calculateTotalPrice());
    }
}
