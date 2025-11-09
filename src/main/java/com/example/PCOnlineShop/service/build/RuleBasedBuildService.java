package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.dto.build.BuildPlanDto;
import com.example.PCOnlineShop.dto.build.ComponentDto;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.build.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RuleBasedBuildService {

    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;
    private final MainboardRepository mainboardRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CaseRepository caseRepository;
    private final CoolingRepository coolingRepository;
    private final CompatibilityService compatibilityService;

    public BuildPlanDto suggestBuild(String presetName, double totalBudget) {
        log.info("🎯 Suggesting build for preset: {}, budget: ${}", presetName, totalBudget);

        // 1. Get preset
        BuildPreset preset = BuildPreset.valueOf(presetName.toUpperCase().replace(" ", "_").replace("-", "_"));

        // 2. Validate budget
        if (totalBudget < preset.getSuggestedMinBudget()) {
            log.warn("⚠️ Budget ${} is below recommended ${}", totalBudget, preset.getSuggestedMinBudget());
        }

        // 3. Create temporary BuildItemDto for compatibility checking
        BuildItemDto tempBuild = new BuildItemDto();

        // 4. Select components in FIXED ORDER with compatibility check
        // ORDER IS CRITICAL: Each component depends on previous selections
        // - Mainboard: Base component (defines socket, DDR type, PCIe, M.2, SATA, form factor)
        // - CPU: Must match mainboard socket (performance score + compatibility)
        // - Memory: Must match mainboard DDR type and slots (performance score + compatibility)
        // - GPU: Must match PCIe version (performance score + compatibility)
        // - Storage: Must have compatible interface M.2/SATA (performance score + compatibility)
        // - PSU: Must provide sufficient wattage for all components (compatibility ONLY, no score)
        // - Cooling: Must fit in case (compatibility ONLY, no score)
        // - Case: Must support mainboard form factor and PSU form factor (compatibility ONLY, no score)

        // Step 1: Select Mainboard first (base for all compatibility)
        Mainboard mainboard = selectMainboardEntity(preset, totalBudget, tempBuild);
        if (mainboard == null) {
            log.error("❌ Cannot build - No mainboard found!");
            return createEmptyPlan(preset, totalBudget);
        }
        tempBuild.setMainboard(mainboard);

        // Step 2: Select CPU (filtered by budget, checked for socket compatibility)
        CPU cpu = selectCpuEntity(preset, totalBudget, tempBuild);
        if (cpu == null) {
            log.error("❌ Cannot build - No compatible CPU found for socket: {}", mainboard.getSocket());
            return createEmptyPlan(preset, totalBudget);
        }
        tempBuild.setCpu(cpu);

        // Step 3: Select Memory (filtered by budget, checked for DDR type and slots)
        Memory memory = selectMemoryEntity(preset, totalBudget, tempBuild);
        if (memory == null) {
            log.error("❌ Cannot build - No compatible Memory found for type: {}", mainboard.getMemoryType());
        }
        tempBuild.setMemory(memory);

        // Step 4: Select GPU (filtered by budget, checked for PCIe compatibility)
        GPU gpu = selectGpuEntity(preset, totalBudget, tempBuild);
        if (gpu == null) {
            log.warn("⚠️ No compatible GPU found - continuing without GPU");
        }
        tempBuild.setGpu(gpu);

        // Step 5: Select Storage (filtered by budget, checked for interface support)
        Storage storage = selectStorageEntity(preset, totalBudget, tempBuild);
        if (storage == null) {
            log.warn("⚠️ No compatible Storage found");
        }
        tempBuild.setStorage(storage);

        // Step 6: Select PSU (compatibility-based: sufficient wattage + form factor match)
        PowerSupply psu = selectPsuEntity(preset, totalBudget, tempBuild);
        tempBuild.setPowerSupply(psu);

        // Step 7: Select Cooling (compatibility-based: size match with case)
        Cooling cooling = selectCoolingEntity(preset, totalBudget, tempBuild);
        tempBuild.setCooling(cooling);

        // Step 8: Select Case (compatibility-based: form factor + GPU length + PSU + cooling)
        Case pcCase = selectCaseEntity(preset, totalBudget, tempBuild);
        tempBuild.setPcCase(pcCase);

        // 5. Convert to BuildPlanDto for frontend
        BuildPlanDto plan = convertToPlanDto(tempBuild, preset, totalBudget);

        log.info("✅ Build suggestion completed with compatibility checks");
        return plan;
    }

    private BuildPlanDto createEmptyPlan(BuildPreset preset, double totalBudget) {
        return BuildPlanDto.builder()
            .totalBudget(totalBudget)
            .purpose(preset.getName())
            .build();
    }

    private BuildPlanDto convertToPlanDto(BuildItemDto items, BuildPreset preset, double totalBudget) {
        BuildPlanDto plan = BuildPlanDto.builder()
            .totalBudget(totalBudget)
            .purpose(preset.getName())
            .build();

        if (items.getMainboard() != null) {
            plan.setMainboard(entityToDto(items.getMainboard().getProduct(), "Mainboard"));
        }
        if (items.getCpu() != null) {
            plan.setCpu(entityToDto(items.getCpu().getProduct(), "CPU"));
        }
        if (items.getGpu() != null) {
            plan.setGpu(entityToDto(items.getGpu().getProduct(), "GPU"));
        }
        if (items.getMemory() != null) {
            plan.setMemory(entityToDto(items.getMemory().getProduct(), "Memory"));
        }
        if (items.getStorage() != null) {
            plan.setStorage(entityToDto(items.getStorage().getProduct(), "Storage"));
        }
        if (items.getPowerSupply() != null) {
            plan.setPowerSupply(entityToDto(items.getPowerSupply().getProduct(), "PowerSupply"));
        }
        if (items.getCooling() != null) {
            plan.setCooling(entityToDto(items.getCooling().getProduct(), "Cooling"));
        }
        if (items.getPcCase() != null) {
            plan.setPcCase(entityToDto(items.getPcCase().getProduct(), "Case"));
        }

        return plan;
    }

    private ComponentDto entityToDto(com.example.PCOnlineShop.model.product.Product product, String category) {
        return ComponentDto.builder()
            .productId((long) product.getProductId())
            .productName(product.getProductName())
            .price(product.getPrice())
            .score(product.getPerformanceScore())
            .category(category)
            .build();
    }

    // ============================================
    // COMPONENT SELECTION: Budget-filtered + Compatibility-checked
    // Order: Mainboard → CPU → Memory → GPU → Storage → PSU → Cooling → Case
    // ============================================

    private Mainboard selectMainboardEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("mainboard", totalBudget);
        // Set minimum score based on budget level to get better quality boards
        int minScore = totalBudget >= 1500 ? 60 : (totalBudget >= 1000 ? 50 : 40);

        log.debug("🔌 Selecting Mainboard: budget=${}, minScore={} (Consider both score and price)", budget, minScore);

        List<Mainboard> mainboards = mainboardRepository.findBestMainboardsByBudgetAndScore(budget, minScore);

        if (mainboards.isEmpty()) {
            log.warn("⚠️ No mainboard with score >= {} in budget ${}, trying with lower score", minScore, budget);
            mainboards = mainboardRepository.findBestMainboardsByBudgetAndScore(budget * 1.3, minScore - 20);
        }

        if (mainboards.isEmpty()) {
            log.warn("⚠️ Still no mainboard, relaxing all constraints");
            mainboards = mainboardRepository.findBestMainboardsByBudgetAndScore(budget * 1.5, 0);
        }

        log.debug("🎯 Looking for mainboard with highest score in full budget: ${} with minScore >= {}", budget, minScore);

        // Find mainboard with highest score in entire budget (100% like CPU/GPU)
        int bestScore = 0;
        Mainboard bestMainboard = null;

        for (Mainboard mb : mainboards) {
            int score = mb.getProduct().getPerformanceScore();
            if (score > bestScore) {
                bestScore = score;
                bestMainboard = mb;
            }
        }

        if (bestMainboard != null) {
            log.info("✅ Selected Mainboard (highest score): {} - ${} (Socket: {}, Score: {})",
                     bestMainboard.getProduct().getProductName(),
                     bestMainboard.getProduct().getPrice(),
                     bestMainboard.getSocket(),
                     bestMainboard.getProduct().getPerformanceScore());
            return bestMainboard;
        }

        log.error("❌ No Mainboard found!");
        return null;
    }

    private CPU selectCpuEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("cpu", totalBudget);
        int minScore = Math.max(preset.getRequirement("cpu_score_min") - 20, 40);

        log.debug("💻 Selecting CPU: budget=${}, minScore={}", budget, minScore);

        List<CPU> cpus = cpuRepository.findBestCpusByBudgetAndScore(budget, minScore);

        if (cpus.isEmpty()) {
            cpus = cpuRepository.findBestCpusByBudgetAndScore(budget * 1.3, 30);
        }

        if (cpus.isEmpty()) {
            cpus = cpuRepository.findBestCpusByBudgetAndScore(budget * 1.5, 0);
        }

        // Find first compatible CPU
        for (CPU cpu : cpus) {
            if (compatibilityService.checkCpuCompatibility(tempBuild, cpu)) {
                log.info("✅ Selected CPU: {} - ${} (Socket: {}, Compatible: ✓)",
                         cpu.getProduct().getProductName(),
                         cpu.getProduct().getPrice(),
                         cpu.getSocket());
                return cpu;
            } else {
                log.debug("⚠️ CPU {} incompatible - Socket mismatch", cpu.getProduct().getProductName());
            }
        }

        log.error("❌ No compatible CPU found!");
        return null;
    }

    private Memory selectMemoryEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("memory", totalBudget);
        int minScore = 30;

        log.debug("🧠 Selecting Memory: budget=${}", budget);

        List<Memory> memories = memoryRepository.findBestMemoryByBudgetAndScore(budget, minScore);

        if (memories.isEmpty()) {
            memories = memoryRepository.findBestMemoryByBudgetAndScore(budget * 1.5, 0);
        }

        // Find first compatible memory using CompatibilityService
        for (Memory memory : memories) {
            if (compatibilityService.checkMemoryCompatibility(tempBuild, memory)) {
                log.info("✅ Selected Memory: {} - ${} (Type: {}, Modules: {}, Compatible: ✓)",
                         memory.getProduct().getProductName(),
                         memory.getProduct().getPrice(),
                         memory.getType(),
                         memory.getModules());
                return memory;
            } else {
                log.debug("⚠️ Memory {} incompatible", memory.getProduct().getProductName());
            }
        }

        log.error("❌ No compatible Memory found!");
        return null;
    }

    private GPU selectGpuEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("gpu", totalBudget);
        int minScore = Math.max(preset.getRequirement("gpu_score_min") - 20, 40);

        log.debug("🎮 Selecting GPU: budget=${}, minScore={}", budget, minScore);

        List<GPU> gpus = gpuRepository.findBestGpusByBudgetAndScore(budget, minScore);

        if (gpus.isEmpty()) {
            gpus = gpuRepository.findBestGpusByBudgetAndScore(budget * 1.3, 30);
        }

        if (gpus.isEmpty()) {
            gpus = gpuRepository.findBestGpusByBudgetAndScore(budget * 1.5, 0);
        }

        // Find first compatible GPU
        for (GPU gpu : gpus) {
            if (compatibilityService.checkGpuCompatibility(tempBuild, gpu)) {
                log.info("✅ Selected GPU: {} - ${} (Compatible: ✓)",
                         gpu.getProduct().getProductName(),
                         gpu.getProduct().getPrice());
                return gpu;
            } else {
                log.debug("⚠️ GPU {} incompatible - PCIe version mismatch", gpu.getProduct().getProductName());
            }
        }

        log.error("❌ No compatible GPU found!");
        return null;
    }

    private Storage selectStorageEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("storage", totalBudget);
        int minScore = 30;

        log.debug("💾 Selecting Storage: budget=${} (Select mid-to-high range for better performance)", budget);

        List<Storage> storages = storageRepository.findBestStorageByBudgetAndScore(budget, minScore);

        if (storages.isEmpty()) {
            storages = storageRepository.findBestStorageByBudgetAndScore(budget * 1.5, 0);
        }

        // Define price range: prefer storages between 60-100% of budget for better performance
        double minPreferredPrice = budget * 0.6;
        double maxPreferredPrice = budget;

        log.debug("🎯 Looking for storage in preferred range: ${} - ${}", minPreferredPrice, maxPreferredPrice);

        // First pass: Try to find compatible storage in preferred price range
        for (Storage storage : storages) {
            double price = storage.getProduct().getPrice();
            if (price >= minPreferredPrice && price <= maxPreferredPrice) {
                if (compatibilityService.checkStorageCompatibility(tempBuild, storage)) {
                    log.info("✅ Selected Storage (preferred range): {} - ${} (Interface: {}, Capacity: {}GB)",
                             storage.getProduct().getProductName(),
                             storage.getProduct().getPrice(),
                             storage.getInterfaceType(),
                             storage.getCapacity());
                    return storage;
                }
            }
        }

        log.debug("⚠️ No storage found in preferred range, trying any compatible storage in budget");

        // Second pass: Use CompatibilityService to check storage compatibility (any price in budget)
        for (Storage storage : storages) {
            if (compatibilityService.checkStorageCompatibility(tempBuild, storage)) {
                log.info("✅ Selected Storage: {} - ${} (Interface: {}, Compatible: ✓)",
                         storage.getProduct().getProductName(),
                         storage.getProduct().getPrice(),
                         storage.getInterfaceType());
                return storage;
            } else {
                log.debug("⚠️ Storage {} incompatible - Interface not supported", storage.getProduct().getProductName());
            }
        }

        log.error("❌ No compatible Storage found!");
        return null;
    }

    private PowerSupply selectPsuEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("psu", totalBudget);

        log.debug("⚡ Selecting PSU: budget=${} (Score not considered, only compatibility)", budget);

        // Get all PSUs sorted by price (no score filtering)
        List<PowerSupply> psus = powerSupplyRepository.findBestPsuByBudgetAndScore(budget, 0);

        if (psus.isEmpty()) {
            log.warn("⚠️ No PSU found in budget ${}, relaxing to ${}", budget, budget * 1.5);
            psus = powerSupplyRepository.findBestPsuByBudgetAndScore(budget * 1.5, 0);
        }

        // Find first PSU that is compatible using CompatibilityService
        for (PowerSupply psu : psus) {
            if (compatibilityService.checkPowerSupplyCompatibility(tempBuild, psu)) {
                log.info("✅ Selected PSU: {} - ${} ({}W, Compatible: ✓)",
                         psu.getProduct().getProductName(),
                         psu.getProduct().getPrice(),
                         psu.getWattage());
                return psu;
            } else {
                log.debug("⚠️ PSU {} incompatible - Insufficient wattage or form factor mismatch",
                         psu.getProduct().getProductName());
            }
        }

        // If no PSU meets requirement in budget, find any PSU that can handle the load
        log.warn("⚠️ No PSU in budget is compatible, searching all PSUs");
        psus = powerSupplyRepository.findAll();
        for (PowerSupply psu : psus) {
            if (compatibilityService.checkPowerSupplyCompatibility(tempBuild, psu)) {
                log.info("✅ Selected PSU (over budget): {} - ${} ({}W, Compatible: ✓)",
                         psu.getProduct().getProductName(),
                         psu.getProduct().getPrice(),
                         psu.getWattage());
                return psu;
            }
        }

        log.error("❌ No compatible PSU found!");
        return null;
    }

    private Cooling selectCoolingEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("cooling", totalBudget);

        log.debug("❄️ Selecting Cooling: budget=${} (Score not considered, only compatibility)", budget);

        // Get all coolings sorted by price (no score filtering)
        List<Cooling> coolings = coolingRepository.findBestCoolingByBudgetAndScore(budget, 0);

        if (coolings.isEmpty()) {
            coolings = coolingRepository.findBestCoolingByBudgetAndScore(budget * 1.5, 0);
        }

        // Find first compatible cooling using CompatibilityService
        for (Cooling cooling : coolings) {
            if (compatibilityService.checkCoolingCompatibility(tempBuild, cooling)) {
                log.info("✅ Selected Cooling: {} - ${} (Type: {}, Compatible: ✓)",
                         cooling.getProduct().getProductName(),
                         cooling.getProduct().getPrice(),
                         cooling.getType());
                return cooling;
            } else {
                log.debug("⚠️ Cooling {} incompatible - Size mismatch with case",
                         cooling.getProduct().getProductName());
            }
        }

        log.error("❌ No compatible Cooling found!");
        return null;
    }

    private Case selectCaseEntity(BuildPreset preset, double totalBudget, BuildItemDto tempBuild) {
        double budget = preset.calculateComponentBudget("case", totalBudget);

        log.debug("📦 Selecting Case: budget=${} (Select mid-to-high range for better quality)", budget);

        // Get all cases sorted by price (no score filtering)
        List<Case> cases = caseRepository.findBestCasesByBudgetAndScore(budget, 0);

        log.info("📦 Found {} cases within budget ${}", cases.size(), budget);

        // Debug: Show current build state
        log.info("🔍 Current build state for case compatibility:");
        if (tempBuild.getMainboard() != null) {
            log.info("   Mainboard: {} (Form: {})",
                     tempBuild.getMainboard().getProduct().getProductName(),
                     tempBuild.getMainboard().getFormFactor());
        }
        if (tempBuild.getGpu() != null) {
            log.info("   GPU: {} (Length: {}mm)",
                     tempBuild.getGpu().getProduct().getProductName(),
                     tempBuild.getGpu().getLength());
        }
        if (tempBuild.getCooling() != null) {
            log.info("   Cooling: {} (Type: {}, FanSize: {}, Radiator: {})",
                     tempBuild.getCooling().getProduct().getProductName(),
                     tempBuild.getCooling().getType(),
                     tempBuild.getCooling().getFanSize(),
                     tempBuild.getCooling().getRadiatorSize());
        }
        if (tempBuild.getPowerSupply() != null) {
            log.info("   PSU: {} (Form Factor: {})",
                     tempBuild.getPowerSupply().getProduct().getProductName(),
                     tempBuild.getPowerSupply().getFormFactor());
        }

        // Try 1: Find compatible case within budget - prefer mid-to-high price range
        Case selectedCase = findBestValueCase(cases, tempBuild, budget);
        if (selectedCase != null) {
            log.info("✅ Selected Case: {} - ${} (Form Factor: {}, Compatible: ✓)",
                     selectedCase.getProduct().getProductName(),
                     selectedCase.getProduct().getPrice(),
                     selectedCase.getFormFactor());
            return selectedCase;
        }

        // Try 2: No compatible case in budget, try 2x budget
        log.warn("⚠️ No Case found in budget ${}, trying with 2x budget ${}", budget, budget * 2.0);
        cases = caseRepository.findBestCasesByBudgetAndScore(budget * 2.0, 0);
        log.info("📦 Found {} cases within 2x budget ${}", cases.size(), budget * 2.0);

        selectedCase = findCompatibleCase(cases, tempBuild);
        if (selectedCase != null) {
            log.info("✅ Selected Case (2x budget): {} - ${} (Form Factor: {}, Compatible: ✓)",
                     selectedCase.getProduct().getProductName(),
                     selectedCase.getProduct().getPrice(),
                     selectedCase.getFormFactor());
            return selectedCase;
        }

        // Try 3: No compatible case even in 2x budget, try 4x budget
        log.warn("⚠️ No Case found in 2x budget, trying with 4x budget ${}", budget * 4.0);
        cases = caseRepository.findBestCasesByBudgetAndScore(budget * 4.0, 0);
        log.info("📦 Found {} cases within 4x budget ${}", cases.size(), budget * 4.0);

        selectedCase = findCompatibleCase(cases, tempBuild);
        if (selectedCase != null) {
            log.info("✅ Selected Case (4x budget): {} - ${} (Form Factor: {}, Compatible: ✓)",
                     selectedCase.getProduct().getProductName(),
                     selectedCase.getProduct().getPrice(),
                     selectedCase.getFormFactor());
            return selectedCase;
        }

        // Try 4: Last resort - search ALL cases, find cheapest compatible one
        log.warn("⚠️ No Case found even in 4x budget, searching ALL cases");
        cases = caseRepository.findAll();
        log.info("📦 Found {} total cases in database", cases.size());

        selectedCase = findCompatibleCase(cases, tempBuild);
        if (selectedCase != null) {
            log.info("✅ Selected Case (any price): {} - ${} (Form Factor: {}, Compatible: ✓)",
                     selectedCase.getProduct().getProductName(),
                     selectedCase.getProduct().getPrice(),
                     selectedCase.getFormFactor());
            return selectedCase;
        }

        log.error("❌ No compatible Case found in entire database!");
        return null;
    }

    /**
     * Helper method to find best value case - prefer mid-to-high price range
     * Instead of selecting cheapest, select cases in 50-100% of budget for better quality
     */
    private Case findBestValueCase(List<Case> cases, BuildItemDto tempBuild, double budget) {
        // Define price range: prefer cases between 50-100% of budget
        double minPreferredPrice = budget * 0.5;
        double maxPreferredPrice = budget;

        log.debug("🎯 Looking for case in preferred range: ${} - ${}", minPreferredPrice, maxPreferredPrice);

        // First pass: Try to find compatible case in preferred price range
        for (Case pcCase : cases) {
            double price = pcCase.getProduct().getPrice();
            if (price >= minPreferredPrice && price <= maxPreferredPrice) {
                if (compatibilityService.checkCaseCompatibility(tempBuild, pcCase)) {
                    log.info("🎯 Found case in preferred price range: {} - ${}",
                             pcCase.getProduct().getProductName(), price);
                    return pcCase;
                }
            }
        }

        log.debug("⚠️ No case found in preferred range, trying any compatible case in budget");

        // Second pass: If no case in preferred range, accept any compatible case in budget
        return findCompatibleCase(cases, tempBuild);
    }

    /**
     * Helper method to find first compatible case from a list
     */
    private Case findCompatibleCase(List<Case> cases, BuildItemDto tempBuild) {
        int checkedCount = 0;
        for (Case pcCase : cases) {
            checkedCount++;
            log.debug("   Checking case {}/{}: {} - ${} (Form: {}, PSU: {}, GPU Max: {}mm, CPU Max: {}mm)",
                     checkedCount, cases.size(),
                     pcCase.getProduct().getProductName(),
                     pcCase.getProduct().getPrice(),
                     pcCase.getFormFactor(),
                     pcCase.getPsuFormFactor(),
                     pcCase.getGpuMaxLength(),
                     pcCase.getCpuMaxCoolerHeight());

            if (compatibilityService.checkCaseCompatibility(tempBuild, pcCase)) {
                return pcCase; // Found compatible case
            }
        }

        log.debug("   No compatible case found in this list ({} cases checked)", checkedCount);
        return null; // No compatible case found in this list
    }


    /**
     * Convert BuildPlanDto (with ComponentDto) to BuildItemDto (with actual entities)
     * for storing in session
     */
    public BuildItemDto convertPlanToItems(BuildPlanDto plan) {
        log.info("🔄 Converting BuildPlanDto to BuildItemDto for session storage");

        BuildItemDto items = new BuildItemDto();

        // Convert each component by fetching from database
        if (plan.getCpu() != null) {
            items.setCpu(cpuRepository.findByProduct_ProductId(plan.getCpu().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getGpu() != null) {
            items.setGpu(gpuRepository.findByProduct_ProductId(plan.getGpu().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getMainboard() != null) {
            items.setMainboard(mainboardRepository.findByProduct_ProductId(plan.getMainboard().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getMemory() != null) {
            items.setMemory(memoryRepository.findByProduct_ProductId(plan.getMemory().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getStorage() != null) {
            items.setStorage(storageRepository.findByProduct_ProductId(plan.getStorage().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getPowerSupply() != null) {
            items.setPowerSupply(powerSupplyRepository.findByProduct_ProductId(plan.getPowerSupply().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getPcCase() != null) {
            items.setPcCase(caseRepository.findByProduct_ProductId(plan.getPcCase().getProductId().intValue())
                .orElse(null));
        }

        if (plan.getCooling() != null) {
            items.setCooling(coolingRepository.findByProduct_ProductId(plan.getCooling().getProductId().intValue())
                .orElse(null));
        }

        log.info("✅ BuildItemDto created successfully");
        return items;
    }
}

