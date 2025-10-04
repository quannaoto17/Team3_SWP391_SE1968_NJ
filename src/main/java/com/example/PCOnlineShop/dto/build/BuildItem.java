package com.example.PCOnlineShop.dto.build;

import com.example.PCOnlineShop.model.build.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildItem {
    Mainboard mainboard;
    CPU cpu;
    Memory memory;
    GPU gpu;
    Storage storage;
    PowerSupply powerSupply;
    Case pcCase;
    Cooling cooling;
    Other other;
}
