package model.build;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class BuildItem {
    private CPU cpu;
    private GPU gpu;
    private Memory memory;
    private Storage storage;
    private Motherboard motherboard;
    private PowerSupply powerSupply;
    private Case pcCase;
    private Cooling cooling;
    private List<Other> others;
}
