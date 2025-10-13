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
    private Other other;

    public boolean isEmpty() {
        return mainboard == null && cpu == null && memory == null && gpu == null &&
               storage == null && powerSupply == null && pcCase == null &&
               cooling == null && other == null;
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
        if (other != null) total += other.getPrice();
        return total;
    }
}
