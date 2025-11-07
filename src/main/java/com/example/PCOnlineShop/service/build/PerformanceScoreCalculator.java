package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.*;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service to calculate performance scores for PC components
 * Based on specifications and benchmarks
 */
@Service
public class PerformanceScoreCalculator {

    /**
     * Calculate CPU performance score (0-100)
     * Based on: cores, threads, base/boost clock, generation
     * Extracts additional info from product description using regex
     */
    public int calculateCpuScore(CPU cpu) {
        int score = 0;
        String description = cpu.getProduct() != null ? cpu.getProduct().getDescription() : "";
        String productName = cpu.getProduct() != null ? cpu.getProduct().getProductName() : "";

        // Extract cores from description (e.g., "8-Core", "6 cores", "16C")
        int coreCount = extractCoreCount(description, productName);

        // Core count (40 points max)
        if (coreCount >= 16) score += 40;
        else if (coreCount >= 12) score += 35;
        else if (coreCount >= 8) score += 30;
        else if (coreCount >= 6) score += 25;
        else if (coreCount >= 4) score += 20;
        else if (coreCount > 0) score += 10;

        // Extract base clock from description (e.g., "3.6GHz", "4.0 GHz")
        double baseClock = extractBaseClock(description, productName);

        // Base clock (30 points max)
        if (baseClock >= 4.0) score += 30;
        else if (baseClock >= 3.5) score += 25;
        else if (baseClock >= 3.0) score += 20;
        else if (baseClock >= 2.5) score += 15;
        else if (baseClock > 0) score += 10;

        // Architecture/Generation from product name (30 points max)
        String combined = (productName + " " + description).toLowerCase();

        // Intel generations
        if (combined.contains("14th gen") || combined.contains("i9-14") || combined.contains("i7-14") ||
            combined.contains("i5-14") || combined.contains("i3-14")) score += 30;
        else if (combined.contains("13th gen") || combined.contains("i9-13") || combined.contains("i7-13") ||
                 combined.contains("i5-13") || combined.contains("i3-13")) score += 28;
        else if (combined.contains("12th gen") || combined.contains("i9-12") || combined.contains("i7-12") ||
                 combined.contains("i5-12") || combined.contains("i3-12")) score += 25;
        else if (combined.contains("11th gen") || combined.contains("10th gen")) score += 20;

        // AMD Ryzen generations
        else if (combined.contains("ryzen 9 9") || combined.contains("ryzen 7 9") ||
                 combined.contains("ryzen 5 9")) score += 30;
        else if (combined.contains("ryzen 9 7") || combined.contains("ryzen 7 7") ||
                 combined.contains("ryzen 5 7")) score += 28;
        else if (combined.contains("ryzen 9 5") || combined.contains("ryzen 7 5") ||
                 combined.contains("ryzen 5 5")) score += 25;
        else if (combined.contains("ryzen 9 3") || combined.contains("ryzen 7 3") ||
                 combined.contains("ryzen 5 3")) score += 20;
        else score += 15;

        return Math.min(score, 100);
    }

    /**
     * Extract core count from description using regex
     */
    private int extractCoreCount(String description, String productName) {
        String combined = description + " " + productName;

        // Patterns: "8-Core", "8 cores", "8C", "8-core", "8 Core"
        Pattern pattern = Pattern.compile("(\\d+)[-\\s]?(core|cores|c)(?!lockspeed|lock)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(combined);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    /**
     * Extract base clock speed from description using regex
     */
    private double extractBaseClock(String description, String productName) {
        String combined = description + " " + productName;

        // Patterns: "3.6GHz", "3.6 GHz", "Base: 3.6GHz", "3600MHz"
        Pattern pattern = Pattern.compile("(?:base[:\\s]+)?(\\d+\\.\\d+|\\d+)\\s*(?:ghz|mhz)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(combined);

        if (matcher.find()) {
            double value = Double.parseDouble(matcher.group(1));
            // Convert MHz to GHz if needed
            if (combined.toLowerCase().contains("mhz") && value > 100) {
                value = value / 1000.0;
            }
            return value;
        }
        return 0.0;
    }

    /**
     * Calculate GPU performance score (0-100)
     * Based on: VRAM, architecture, chipset/series
     */
    public int calculateGpuScore(GPU gpu) {
        int score = 0;
        String productName = gpu.getProduct() != null ? gpu.getProduct().getProductName() : "";
        String description = gpu.getProduct() != null ? gpu.getProduct().getDescription() : "";

        // VRAM from model (30 points max)
        int vram = gpu.getVram();
        if (vram >= 24) score += 30;
        else if (vram >= 16) score += 28;
        else if (vram >= 12) score += 25;
        else if (vram >= 8) score += 20;
        else if (vram >= 6) score += 15;
        else if (vram >= 4) score += 10;
        else score += 5;

        // Chipset/Series from product name (70 points max)
        String combined = (productName + " " + description).toLowerCase();

        // NVIDIA RTX 40 series
        if (combined.contains("rtx 4090") || combined.contains("4090")) score += 70;
        else if (combined.contains("rtx 4080") || combined.contains("4080")) score += 65;
        else if (combined.contains("rtx 4070") || combined.contains("4070")) score += 60;
        else if (combined.contains("rtx 4060") || combined.contains("4060")) score += 55;

        // NVIDIA RTX 30 series
        else if (combined.contains("rtx 3090") || combined.contains("3090")) score += 65;
        else if (combined.contains("rtx 3080") || combined.contains("3080")) score += 60;
        else if (combined.contains("rtx 3070") || combined.contains("3070")) score += 55;
        else if (combined.contains("rtx 3060") || combined.contains("3060")) score += 50;

        // AMD RX 7000 series
        else if (combined.contains("rx 7900") || combined.contains("7900 xt")) score += 65;
        else if (combined.contains("rx 7800") || combined.contains("7800 xt")) score += 60;
        else if (combined.contains("rx 7700") || combined.contains("7700 xt")) score += 55;
        else if (combined.contains("rx 7600") || combined.contains("7600")) score += 50;

        // AMD RX 6000 series
        else if (combined.contains("rx 6950") || combined.contains("6950 xt")) score += 62;
        else if (combined.contains("rx 6900") || combined.contains("6900 xt")) score += 60;
        else if (combined.contains("rx 6800") || combined.contains("6800 xt")) score += 55;
        else if (combined.contains("rx 6700") || combined.contains("6700 xt")) score += 50;
        else if (combined.contains("rx 6600") || combined.contains("6600 xt")) score += 45;
        else if (combined.contains("rx 6500") || combined.contains("6500 xt")) score += 35;
        else if (combined.contains("rx 6400")) score += 30;

        // NVIDIA GTX 16 series
        else if (combined.contains("gtx 1660") || combined.contains("1660")) score += 40;
        else if (combined.contains("gtx 1650") || combined.contains("1650")) score += 35;

        // AMD RX 5000 series
        else if (combined.contains("rx 5700") || combined.contains("5700 xt")) score += 45;
        else if (combined.contains("rx 5600") || combined.contains("5600 xt")) score += 40;
        else if (combined.contains("rx 5500") || combined.contains("5500 xt")) score += 35;

        else score += 30; // Default for unknown GPUs

        return Math.min(score, 100);
    }

    /**
     * Calculate RAM performance score (0-100)
     * Based on: capacity, speed, type
     */
    public int calculateMemoryScore(Memory memory) {
        int score = 0;

        // Capacity from model (50 points max)
        int capacity = memory.getCapacity();
        if (capacity >= 128) score += 50;
        else if (capacity >= 64) score += 48;
        else if (capacity >= 32) score += 45;
        else if (capacity >= 16) score += 40;
        else if (capacity >= 8) score += 30;
        else score += 20;

        // Speed from model (50 points max)
        int speed = memory.getSpeed();
        if (speed >= 6400) score += 50;
        else if (speed >= 6000) score += 48;
        else if (speed >= 5600) score += 45;
        else if (speed >= 5200) score += 43;
        else if (speed >= 4800) score += 40;
        else if (speed >= 4000) score += 38;
        else if (speed >= 3600) score += 35;
        else if (speed >= 3200) score += 30;
        else if (speed >= 2666) score += 25;
        else score += 20;

        return Math.min(score, 100);
    }

    /**
     * Calculate Storage performance score (0-100)
     * Based on: capacity, type, interface, read speed
     */
    public int calculateStorageScore(Storage storage) {
        int score = 0;

        // Type from model (40 points max)
        String type = storage.getType();
        if (type != null) {
            type = type.toLowerCase();
            if (type.contains("nvme") || type.contains("m.2")) score += 40;
            else if (type.contains("ssd")) score += 30;
            else if (type.contains("hdd")) score += 15;
            else score += 10;
        }

        // Capacity from model in GB (30 points max)
        int capacity = storage.getCapacity();
        if (capacity >= 4000) score += 30; // 4TB+
        else if (capacity >= 2000) score += 28; // 2TB
        else if (capacity >= 1000) score += 25; // 1TB
        else if (capacity >= 500) score += 20; // 500GB
        else if (capacity >= 250) score += 15; // 250GB
        else score += 10;

        // Read Speed from model (30 points max)
        int readSpeed = storage.getReadSpeed();
        if (readSpeed >= 7000) score += 30; // Gen 4/5 NVMe
        else if (readSpeed >= 5000) score += 25; // Gen 4 NVMe
        else if (readSpeed >= 3500) score += 22; // Gen 3 NVMe (high-end)
        else if (readSpeed >= 3000) score += 20; // Gen 3 NVMe
        else if (readSpeed >= 2000) score += 18; // Gen 3 NVMe (entry)
        else if (readSpeed >= 550) score += 15; // SATA SSD
        else if (readSpeed >= 500) score += 12; // SATA SSD (entry)
        else score += 10; // HDD or low-end

        return Math.min(score, 100);
    }

    /**
     * Calculate PSU performance score (0-100)
     * Based on: wattage, efficiency rating, modularity
     */
    public int calculatePsuScore(PowerSupply psu) {
        int score = 0;

        // Wattage from model (50 points max)
        int wattage = psu.getWattage();
        if (wattage >= 1000) score += 50;
        else if (wattage >= 850) score += 45;
        else if (wattage >= 750) score += 40;
        else if (wattage >= 650) score += 35;
        else if (wattage >= 550) score += 30;
        else if (wattage >= 450) score += 25;
        else score += 20;

        // Efficiency from model (40 points max)
        String efficiency = psu.getEfficiency();
        if (efficiency != null) {
            efficiency = efficiency.toLowerCase();
            if (efficiency.contains("titanium")) score += 40;
            else if (efficiency.contains("platinum")) score += 35;
            else if (efficiency.contains("gold")) score += 30;
            else if (efficiency.contains("silver")) score += 25;
            else if (efficiency.contains("bronze")) score += 20;
            else score += 15;
        }

        // Modularity from model (10 points max)
        if (psu.isModular()) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    /**
     * Calculate Case performance score (0-100)
     * Based on: form factor, max GPU length, max cooler height
     */
    public int calculateCaseScore(Case pcCase) {
        int score = 50; // Base score

        // Form factor from model (30 points max)
        String formFactor = pcCase.getFormFactor();
        if (formFactor != null) {
            formFactor = formFactor.toLowerCase();
            if (formFactor.contains("full tower") || formFactor.contains("full-tower")) score += 30;
            else if (formFactor.contains("mid tower") || formFactor.contains("mid-tower")) score += 25;
            else if (formFactor.contains("mini") || formFactor.contains("itx")) score += 15;
            else score += 20;
        }

        // GPU max length (10 points max) - higher is better
        int gpuMaxLength = pcCase.getGpuMaxLength();
        if (gpuMaxLength >= 400) score += 10;
        else if (gpuMaxLength >= 350) score += 8;
        else if (gpuMaxLength >= 300) score += 6;
        else if (gpuMaxLength >= 250) score += 4;
        else score += 2;

        // CPU cooler max height (10 points max) - higher is better
        int cpuMaxCoolerHeight = pcCase.getCpuMaxCoolerHeight();
        if (cpuMaxCoolerHeight >= 180) score += 10;
        else if (cpuMaxCoolerHeight >= 170) score += 8;
        else if (cpuMaxCoolerHeight >= 160) score += 6;
        else if (cpuMaxCoolerHeight >= 150) score += 4;
        else score += 2;

        return Math.min(score, 100);
    }

    /**
     * Calculate Cooling performance score (0-100)
     * Based on: type, radiator size, max TDP
     */
    public int calculateCoolingScore(Cooling cooling) {
        int score = 0;

        // Type from model
        String type = cooling.getType();
        if (type != null && type.toLowerCase().contains("liquid")) {
            score += 30; // AIO gets bonus
        } else {
            score += 20; // Air cooling
        }

        // Radiator size from model (for AIO) (40 points max)
        int radiatorSize = cooling.getRadiatorSize();
        if (radiatorSize >= 360) score += 40;
        else if (radiatorSize >= 280) score += 35;
        else if (radiatorSize >= 240) score += 30;
        else if (radiatorSize >= 120) score += 25;
        else score += 20; // Air cooler or small AIO

        // Max TDP from model (30 points max)
        int maxTdp = cooling.getTdp();
        if (maxTdp >= 250) score += 30;
        else if (maxTdp >= 200) score += 25;
        else if (maxTdp >= 150) score += 20;
        else if (maxTdp >= 100) score += 15;
        else score += 10;

        return Math.min(score, 100);
    }

    /**
     * Calculate Mainboard performance score (0-100)
     * Based on: chipset, PCIe version, memory support
     */
    public int calculateMainboardScore(Mainboard mainboard) {
        int score = 40; // Base score

        // Chipset quality (30 points max)
        String chipset = mainboard.getChipset();
        if (chipset != null) {
            chipset = chipset.toLowerCase();
            // High-end chipsets
            if (chipset.contains("z790") || chipset.contains("z690") || chipset.contains("x670") ||
                chipset.contains("x570") || chipset.contains("b650")) score += 30;
            // Mid-range chipsets
            else if (chipset.contains("b760") || chipset.contains("b660") || chipset.contains("b550") ||
                     chipset.contains("h670")) score += 25;
            // Entry-level chipsets
            else if (chipset.contains("h610") || chipset.contains("a620") || chipset.contains("b450")) score += 20;
            else score += 15;
        }

        // PCIe version (20 points max)
        String pcieVersion = mainboard.getPcieVersion();
        if (pcieVersion != null) {
            if (pcieVersion.contains("5.0")) score += 20;
            else if (pcieVersion.contains("4.0")) score += 15;
            else if (pcieVersion.contains("3.0")) score += 10;
            else score += 5;
        }

        // Memory slots (10 points max)
        int memorySlots = mainboard.getMemorySlots();
        if (memorySlots >= 4) score += 10;
        else if (memorySlots >= 2) score += 5;

        return Math.min(score, 100);
    }
}

