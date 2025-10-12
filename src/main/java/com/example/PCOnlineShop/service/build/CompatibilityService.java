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

    public boolean checkCaseCompatibility(BuildItemDto buildItem, Case pcCase) {
        Mainboard mainboard = buildItem.getMainboard();
        GPU gpu = buildItem.getGpu();

        // Kiểm tra tương thích form factor với mainboard
        if (mainboard != null) {
            if (!isCaseFormFactorCompatible(pcCase.getFormFactor(), mainboard.getFormFactor())) {
                return false;
            }
        }

        // Kiểm tra độ dài GPU có phù hợp với case
        if (gpu != null && pcCase.getGpuMaxLength() < gpu.getLength()) {
            return false;
        }

        return true;
    }

    private boolean isCaseFormFactorCompatible(String caseFormFactor, String mbFormFactor) {
        caseFormFactor = caseFormFactor.toUpperCase();
        mbFormFactor = mbFormFactor.toUpperCase();

        // Case ATX có thể chứa được tất cả loại mainboard
        if (caseFormFactor.contains("ATX")) {
            return true;
        }

        // Case Micro-ATX có thể chứa Micro-ATX và Mini-ITX
        if (caseFormFactor.contains("MICRO")) {
            return mbFormFactor.contains("MICRO") || mbFormFactor.contains("MINI");
        }

        // Case Mini-ITX chỉ có thể chứa Mini-ITX
        if (caseFormFactor.contains("MINI")) {
            return mbFormFactor.contains("MINI");
        }

        return caseFormFactor.equals(mbFormFactor);
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
