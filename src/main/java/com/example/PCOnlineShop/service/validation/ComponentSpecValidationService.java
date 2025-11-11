package com.example.PCOnlineShop.service.validation;

import com.example.PCOnlineShop.model.build.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service để validate các thông số kỹ thuật của linh kiện
 * Đảm bảo các giá trị số liệu nằm trong khoảng hợp lý
 */
@Service
public class ComponentSpecValidationService {

    // ================ GPU VALIDATION ================
    public List<String> validateGpu(GPU gpu) {
        List<String> errors = new ArrayList<>();

        // Validate VRAM (2GB - 48GB)
        if (gpu.getVram() < 2 || gpu.getVram() > 48) {
            errors.add("GPU VRAM must be between 2GB and 48GB");
        }

        // Validate Length (150mm - 400mm)
        if (gpu.getLength() < 150 || gpu.getLength() > 450) {
            errors.add("GPU length must be between 150mm and 450mm");
        }

        // Validate TDP (50W - 600W)
        if (gpu.getTdp() < 50 || gpu.getTdp() > 600) {
            errors.add("GPU TDP must be between 50W and 600W");
        }

        // Validate Memory Type
        if (gpu.getMemoryType() != null && !gpu.getMemoryType().isEmpty()) {
            if (!gpu.getMemoryType().matches("GDDR[56]X?")) {
                errors.add("GPU memory type must be GDDR5, GDDR6, or GDDR6X");
            }
        }

        // Validate PCIe Version
        if (gpu.getPcieVersion() != null && !gpu.getPcieVersion().isEmpty()) {
            if (!gpu.getPcieVersion().matches("[345]\\.0")) {
                errors.add("GPU PCIe version must be 3.0, 4.0, or 5.0");
            }
        }

        return errors;
    }

    // ================ CPU VALIDATION ================
    public List<String> validateCpu(CPU cpu) {
        List<String> errors = new ArrayList<>();

        // Validate TDP (35W - 350W)
        if (cpu.getTdp() < 35 || cpu.getTdp() > 350) {
            errors.add("CPU TDP must be between 35W and 350W");
        }

        // Validate Max Memory Speed (1600 - 8000 MHz)
        if (cpu.getMaxMemorySpeed() < 1600 || cpu.getMaxMemorySpeed() > 8000) {
            errors.add("CPU max memory speed must be between 1600MHz and 8000MHz");
        }

        // Validate Memory Channels (1 - 8)
        if (cpu.getMemoryChannels() != null) {
            if (cpu.getMemoryChannels() < 1 || cpu.getMemoryChannels() > 8) {
                errors.add("CPU memory channels must be between 1 and 8");
            }
        }

        // Validate PCIe Version
        if (cpu.getPcieVersion() != null && !cpu.getPcieVersion().isEmpty()) {
            if (!cpu.getPcieVersion().matches("[345]\\.0")) {
                errors.add("CPU PCIe version must be 3.0, 4.0, or 5.0");
            }
        }

        // Validate Socket
        if (cpu.getSocket() != null && !cpu.getSocket().isEmpty()) {
            if (!cpu.getSocket().matches("(AM[45]|LGA\\d{4}|TR4)")) {
                errors.add("CPU socket must be AM4, AM5, LGA1151, LGA1200, LGA1700, or TR4");
            }
        }

        return errors;
    }

    // ================ MAINBOARD VALIDATION ================
    public List<String> validateMainboard(Mainboard mainboard) {
        List<String> errors = new ArrayList<>();

        // Validate Memory Slots (2 - 8)
        if (mainboard.getMemorySlots() < 2 || mainboard.getMemorySlots() > 8) {
            errors.add("Mainboard memory slots must be between 2 and 8");
        }

        // Validate Max Memory Speed (1600 - 8000 MHz)
        if (mainboard.getMaxMemorySpeed() < 1600 || mainboard.getMaxMemorySpeed() > 8000) {
            errors.add("Mainboard max memory speed must be between 1600MHz and 8000MHz");
        }

        // Validate M.2 Slots (0 - 5)
        if (mainboard.getM2Slots() < 0 || mainboard.getM2Slots() > 5) {
            errors.add("Mainboard M.2 slots must be between 0 and 5");
        }

        // Validate SATA Ports (0 - 12)
        if (mainboard.getSataPorts() < 0 || mainboard.getSataPorts() > 12) {
            errors.add("Mainboard SATA ports must be between 0 and 12");
        }

        // Validate PCIe Version
        if (mainboard.getPcieVersion() != null && !mainboard.getPcieVersion().isEmpty()) {
            if (!mainboard.getPcieVersion().matches("[345]\\.0")) {
                errors.add("Mainboard PCIe version must be 3.0, 4.0, or 5.0");
            }
        }

        // Validate Socket
        if (mainboard.getSocket() != null && !mainboard.getSocket().isEmpty()) {
            if (!mainboard.getSocket().matches("(AM[45]|LGA\\d{4}|TR4)")) {
                errors.add("Mainboard socket must be AM4, AM5, LGA1151, LGA1200, LGA1700, or TR4");
            }
        }

        // Validate Memory Type
        if (mainboard.getMemoryType() != null && !mainboard.getMemoryType().isEmpty()) {
            if (!mainboard.getMemoryType().matches("DDR[45]")) {
                errors.add("Mainboard memory type must be DDR4 or DDR5");
            }
        }

        // Validate Form Factor
        if (mainboard.getFormFactor() != null && !mainboard.getFormFactor().isEmpty()) {
            if (!mainboard.getFormFactor().matches("(Mini-ITX|Micro-ATX|ATX|E-ATX)")) {
                errors.add("Mainboard form factor must be Mini-ITX, Micro-ATX, ATX, or E-ATX");
            }
        }

        return errors;
    }

    // ================ MEMORY VALIDATION ================
    public List<String> validateMemory(Memory memory) {
        List<String> errors = new ArrayList<>();

        // Validate Capacity (4GB - 128GB per kit)
        if (memory.getCapacity() < 4 || memory.getCapacity() > 128) {
            errors.add("Memory capacity must be between 4GB and 128GB");
        }

        // Validate Speed (1600 - 8000 MHz)
        if (memory.getSpeed() < 1600 || memory.getSpeed() > 8000) {
            errors.add("Memory speed must be between 1600MHz and 8000MHz");
        }

        // Validate TDP (2W - 20W)
        if (memory.getTdp() < 2 || memory.getTdp() > 20) {
            errors.add("Memory TDP must be between 2W and 20W");
        }

        // Validate Modules (1, 2, or 4)
        if (memory.getModules() != 1 && memory.getModules() != 2 && memory.getModules() != 4) {
            errors.add("Memory modules must be 1, 2, or 4");
        }

        // Validate Type
        if (memory.getType() != null && !memory.getType().isEmpty()) {
            if (!memory.getType().matches("DDR[45]")) {
                errors.add("Memory type must be DDR4 or DDR5");
            }
        }

        return errors;
    }

    // ================ STORAGE VALIDATION ================
    public List<String> validateStorage(Storage storage) {
        List<String> errors = new ArrayList<>();

        // Validate Capacity (128GB - 8TB = 8000GB)
        if (storage.getCapacity() < 128 || storage.getCapacity() > 8000) {
            errors.add("Storage capacity must be between 128GB and 8TB (8000GB)");
        }

        // Validate Read Speed (100 - 14000 MB/s)
        if (storage.getReadSpeed() < 100 || storage.getReadSpeed() > 14000) {
            errors.add("Storage read speed must be between 100MB/s and 14000MB/s");
        }

        // Validate Write Speed (50 - 12000 MB/s)
        if (storage.getWriteSpeed() < 50 || storage.getWriteSpeed() > 12000) {
            errors.add("Storage write speed must be between 50MB/s and 12000MB/s");
        }

        // Validate Type
        if (storage.getType() != null && !storage.getType().isEmpty()) {
            if (!storage.getType().matches("(SSD|HDD|NVMe)")) {
                errors.add("Storage type must be SSD, HDD, or NVMe");
            }
        }

        // Validate Interface
        if (storage.getInterfaceType() != null && !storage.getInterfaceType().isEmpty()) {
            if (!storage.getInterfaceType().matches("(SATA|NVMe|M\\.2)")) {
                errors.add("Storage interface must be SATA, NVMe, or M.2");
            }
        }

        return errors;
    }

    // ================ POWER SUPPLY VALIDATION ================
    public List<String> validatePowerSupply(PowerSupply psu) {
        List<String> errors = new ArrayList<>();

        // Validate Wattage (300W - 2000W)
        if (psu.getWattage() < 300 || psu.getWattage() > 2000) {
            errors.add("PSU wattage must be between 300W and 2000W");
        }

        // Validate Efficiency
        if (psu.getEfficiency() != null && !psu.getEfficiency().isEmpty()) {
            if (!psu.getEfficiency().matches("80\\+( (Bronze|Silver|Gold|Platinum|Titanium))?")) {
                errors.add("PSU efficiency must be 80+, 80+ Bronze, 80+ Silver, 80+ Gold, 80+ Platinum, or 80+ Titanium");
            }
        }

        // Validate Form Factor
        if (psu.getFormFactor() != null && !psu.getFormFactor().isEmpty()) {
            if (!psu.getFormFactor().matches("(ATX|SFX|SFX-L)")) {
                errors.add("PSU form factor must be ATX, SFX, or SFX-L");
            }
        }

        return errors;
    }

    // ================ CASE VALIDATION ================
    public List<String> validateCase(Case pcCase) {
        List<String> errors = new ArrayList<>();

        // Validate GPU Max Length (150mm - 500mm)
        if (pcCase.getGpuMaxLength() < 150 || pcCase.getGpuMaxLength() > 500) {
            errors.add("Case GPU max length must be between 150mm and 500mm");
        }

        // Validate CPU Max Cooler Height (50mm - 200mm)
        if (pcCase.getCpuMaxCoolerHeight() < 50 || pcCase.getCpuMaxCoolerHeight() > 200) {
            errors.add("Case CPU max cooler height must be between 50mm and 200mm");
        }

        // Validate Form Factor
        if (pcCase.getFormFactor() != null && !pcCase.getFormFactor().isEmpty()) {
            if (!pcCase.getFormFactor().matches("(Mini-ITX|Micro-ATX|ATX|E-ATX)")) {
                errors.add("Case form factor must be Mini-ITX, Micro-ATX, ATX, or E-ATX");
            }
        }

        // Validate PSU Form Factor
        if (pcCase.getPsuFormFactor() != null && !pcCase.getPsuFormFactor().isEmpty()) {
            if (!pcCase.getPsuFormFactor().matches("(ATX|SFX|SFX-L)")) {
                errors.add("Case PSU form factor must be ATX, SFX, or SFX-L");
            }
        }

        return errors;
    }

    // ================ COOLING VALIDATION ================
    public List<String> validateCooling(Cooling cooling) {
        List<String> errors = new ArrayList<>();

        // Validate Fan Size (80mm - 200mm)
        if (cooling.getFanSize() < 80 || cooling.getFanSize() > 200) {
            errors.add("Cooling fan size must be between 80mm and 200mm");
        }

        // Validate Radiator Size (0, 120, 240, 280, 360, 420)
        if (cooling.getRadiatorSize() != 0 &&
            cooling.getRadiatorSize() != 120 &&
            cooling.getRadiatorSize() != 240 &&
            cooling.getRadiatorSize() != 280 &&
            cooling.getRadiatorSize() != 360 &&
            cooling.getRadiatorSize() != 420) {
            errors.add("Cooling radiator size must be 0 (N/A), 120, 240, 280, 360, or 420mm");
        }

        // Validate TDP (5W - 300W for cooling solution itself)
        if (cooling.getTdp() < 5 || cooling.getTdp() > 300) {
            errors.add("Cooling TDP must be between 5W and 300W");
        }

        // Validate Type
        if (cooling.getType() != null && !cooling.getType().isEmpty()) {
            if (!cooling.getType().matches("(Air|AIO)")) {
                errors.add("Cooling type must be Air or AIO");
            }
        }

        return errors;
    }

    // ================ VALIDATE ALL ================
    /**
     * Validate tất cả các component trong một build
     */
    public List<String> validateAllComponents(
            GPU gpu, CPU cpu, Mainboard mainboard, Memory memory,
            Storage storage, PowerSupply psu, Case pcCase, Cooling cooling) {

        List<String> allErrors = new ArrayList<>();

        if (gpu != null) {
            allErrors.addAll(validateGpu(gpu));
        }
        if (cpu != null) {
            allErrors.addAll(validateCpu(cpu));
        }
        if (mainboard != null) {
            allErrors.addAll(validateMainboard(mainboard));
        }
        if (memory != null) {
            allErrors.addAll(validateMemory(memory));
        }
        if (storage != null) {
            allErrors.addAll(validateStorage(storage));
        }
        if (psu != null) {
            allErrors.addAll(validatePowerSupply(psu));
        }
        if (pcCase != null) {
            allErrors.addAll(validateCase(pcCase));
        }
        if (cooling != null) {
            allErrors.addAll(validateCooling(cooling));
        }

        return allErrors;
    }
}

