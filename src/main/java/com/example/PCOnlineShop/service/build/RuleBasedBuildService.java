package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildPlanDto;
import com.example.PCOnlineShop.dto.build.ComponentRule;
import com.example.PCOnlineShop.model.build.BuildPreset;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RuleBasedBuildService {

    private final ProductRepository productRepository;
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;
    private final MainboardRepository mainboardRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CaseRepository caseRepository;
    private final CoolingRepository coolingRepository;

    public BuildPlanDto suggestBuild(String presetName, double totalBudget) {
        log.info("🎯 Suggesting build for preset: {}, budget: ${}", presetName, totalBudget);

        // 1. Get preset
        BuildPreset preset = BuildPreset.valueOf(presetName.toUpperCase().replace(" ", "_").replace("-", "_"));

        // 2. Validate budget
        if (totalBudget < preset.getSuggestedMinBudget()) {
            log.warn("⚠️ Budget ${} is below recommended ${}", totalBudget, preset.getSuggestedMinBudget());
        }

        // 3. Create build plan
        BuildPlanDto plan = new BuildPlanDto();
        plan.setTotalBudget(totalBudget);
        plan.setPurpose(preset.getName());

        // 4. Select components (in order of priority)
        selectGpu(plan, preset, totalBudget);
        selectCpu(plan, preset, totalBudget);
        selectMainboard(plan, preset, totalBudget);
        selectMemory(plan, preset, totalBudget);
        selectStorage(plan, preset, totalBudget);
        selectPsu(plan, preset, totalBudget);
        selectCooling(plan, preset, totalBudget);
        selectCase(plan, preset, totalBudget);

        log.info("✅ Build suggestion completed");
        return plan;
    }

    private void selectGpu(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("gpu", totalBudget);
        int minScore = preset.getRequirement("gpu_score_min");

        log.debug("Selecting GPU: budget=${}, minScore={}", budget, minScore);

        ComponentRule gpuRule = new ComponentRule();
        gpuRule.setBudgetMax(budget);
        gpuRule.setScoreMin(minScore);
        gpuRule.setScoreMax(100);
        plan.setGpu(gpuRule);
    }

    private void selectCpu(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("cpu", totalBudget);
        int minScore = preset.getRequirement("cpu_score_min");

        log.debug("Selecting CPU: budget=${}, minScore={}", budget, minScore);

        ComponentRule cpuRule = new ComponentRule();
        cpuRule.setBudgetMax(budget);
        cpuRule.setScoreMin(minScore);
        cpuRule.setScoreMax(100);
        plan.setCpu(cpuRule);
    }

    private void selectMainboard(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("mainboard", totalBudget);

        log.debug("Selecting Mainboard: budget=${}", budget);

        ComponentRule mainboardRule = new ComponentRule();
        mainboardRule.setBudgetMax(budget);
        mainboardRule.setScoreMin(50);  // Default min score for mainboard
        mainboardRule.setScoreMax(100);
        plan.setMainboard(mainboardRule);
    }

    private void selectMemory(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("memory", totalBudget);
        int minCapacity = preset.getRequirement("memory_capacity_min");
        int minSpeed = preset.getRequirement("memory_speed_min");

        log.debug("Selecting Memory: budget=${}, minCapacity={}GB, minSpeed={}MHz",
                  budget, minCapacity, minSpeed);

        ComponentRule memoryRule = new ComponentRule();
        memoryRule.setBudgetMax(budget);
        memoryRule.setScoreMin(50);  // Default min score
        memoryRule.setScoreMax(100);
        plan.setMemory(memoryRule);
    }

    private void selectStorage(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("storage", totalBudget);
        int minCapacity = preset.getRequirement("storage_capacity_min");

        log.debug("Selecting Storage: budget=${}, minCapacity={}GB", budget, minCapacity);

        ComponentRule storageRule = new ComponentRule();
        storageRule.setBudgetMax(budget);
        storageRule.setScoreMin(50);  // Default min score
        storageRule.setScoreMax(100);
        plan.setStorage(storageRule);
    }

    private void selectPsu(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("psu", totalBudget);
        int minWattage = preset.getRequirement("psu_wattage_min");

        log.debug("Selecting PSU: budget=${}, minWattage={}W", budget, minWattage);

        ComponentRule psuRule = new ComponentRule();
        psuRule.setBudgetMax(budget);
        psuRule.setScoreMin(60);  // PSUs should be decent quality
        psuRule.setScoreMax(100);
        plan.setPsu(psuRule);
    }

    private void selectCooling(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("cooling", totalBudget);

        log.debug("Selecting Cooling: budget=${}", budget);

        ComponentRule coolingRule = new ComponentRule();
        coolingRule.setBudgetMax(budget);
        coolingRule.setScoreMin(50);  // Default min score
        coolingRule.setScoreMax(100);
        plan.setCooling(coolingRule);
    }

    private void selectCase(BuildPlanDto plan, BuildPreset preset, double totalBudget) {
        double budget = preset.calculateComponentBudget("case", totalBudget);

        log.debug("Selecting Case: budget=${}", budget);

        ComponentRule caseRule = new ComponentRule();
        caseRule.setBudgetMax(budget);
        caseRule.setScoreMin(50);  // Default min score
        caseRule.setScoreMax(100);
        plan.setCase(caseRule);
    }
}

