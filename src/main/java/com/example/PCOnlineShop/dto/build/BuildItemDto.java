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
    private double totalPrice;

    public boolean isEmpty() {
        return mainboard == null && cpu == null && memory == null && gpu == null &&
               storage == null && powerSupply == null && pcCase == null &&
               cooling == null && other == null;
    }
}
