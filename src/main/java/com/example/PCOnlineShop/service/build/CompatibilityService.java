package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildItemId;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.build.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompatibilityService {
    private final CpuRepository cpuRepository;
    private final MainboardRepository mainboardRepository;


    public boolean checkMotherboardCpuCompatibility(Mainboard mainboard, CPU cpu) {
        if (!mainboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }
        // Add more compatibility checks as needed
        return true; // Placeholder
    }

    public boolean checkGpuCompatibility(BuildItem buildItem, GPU gpu) {
        CPU cpu = buildItem.getCpu();
        Mainboard mainboard = buildItem.getMainboard();

        if (Math.min(parseVersion(cpu.getPcieVersion()),parseVersion(cpu.getPcieVersion()))
            < parseVersion(gpu.getPcieVersion())) return false;
        return true;
    }

    public static double parseVersion(String version) {
        if (version == null) return 0.0;
        try {
            return Double.parseDouble(version.replace("PCIe", "")
                    .replace(" ", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
