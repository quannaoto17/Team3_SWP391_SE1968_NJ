package com.example.PCOnlineShop.service.build;


import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompatibilityService {

    public  boolean checkMainboardCompatibility(BuildItemDto buildItem, Mainboard mainboard) {
        CPU cpu = buildItem.getCpu();
        if (cpu != null && !mainboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }
        GPU gpu = buildItem.getGpu();
        if (gpu != null && parseVersion(mainboard.getPcieVersion())
                < parseVersion(gpu.getPcieVersion())) {
            return false;
        }
        // Add more compatibility checks as needed
        return true; // Placeholder
    }

    public boolean checkCpuCompatibility(BuildItemDto buildItem, CPU cpu) {
        Mainboard mainboard = buildItem.getMainboard();

        if (!mainboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }
        // Add more compatibility checks as needed
        return true; // Placeholder
    }

    public boolean checkGpuCompatibility(BuildItemDto buildItem, GPU gpu) {
        CPU cpu = buildItem.getCpu();
        Mainboard mainboard = buildItem.getMainboard();

        if (parseVersion(cpu.getPcieVersion())
            < parseVersion(gpu.getPcieVersion())) return false;
        if (parseVersion(mainboard.getPcieVersion())
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
