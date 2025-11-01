package com.example.PCOnlineShop.service.ai;

import com.example.PCOnlineShop.dto.ai.AiPcBuildCriteria;
import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.build.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Service để tạo PC build từ tiêu chí AI
 * ĐÂY LÀ PHIÊN BẢN PHÔI - Logic đơn giản, chưa kiểm tra compatibility đầy đủ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PcBuilderService {

    private final GpuRepository gpuRepository;
    private final CpuRepository cpuRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final MainboardRepository mainboardRepository;
    private final CoolingRepository coolingRepository;
    private final CaseRepository caseRepository;

    /**
     * Tạo PC build từ tiêu chí
     * TODO: Thêm compatibility check sau này
     */
    public BuildItemDto buildFromCriteria(AiPcBuildCriteria criteria) {
        log.info("Building PC from criteria: {}", criteria.getUseCase());

        BuildItemDto build = new BuildItemDto();

        try {
            // Step 1: Chọn GPU
            GPU selectedGpu = selectGpu(criteria);
            if (selectedGpu != null) {
                build.setGpu(selectedGpu);
                log.info("Selected GPU: {}", selectedGpu.getProduct().getProductName());
            }

            // Step 2: Chọn CPU
            CPU selectedCpu = selectCpu(criteria);
            if (selectedCpu != null) {
                build.setCpu(selectedCpu);
                log.info("Selected CPU: {}", selectedCpu.getProduct().getProductName());
            }

            // Step 3: Chọn Mainboard (tạm thời chưa check socket)
            Mainboard selectedMainboard = selectMainboard(criteria);
            if (selectedMainboard != null) {
                build.setMainboard(selectedMainboard);
                log.info("Selected Mainboard: {}", selectedMainboard.getProduct().getProductName());
            }

            // Step 4: Chọn Memory
            Memory selectedMemory = selectMemory(criteria);
            if (selectedMemory != null) {
                build.setMemory(selectedMemory);
                log.info("Selected Memory: {}", selectedMemory.getProduct().getProductName());
            }

            // Step 5: Chọn Storage
            Storage selectedStorage = selectStorage(criteria);
            if (selectedStorage != null) {
                build.setStorage(selectedStorage);
                log.info("Selected Storage: {}", selectedStorage.getProduct().getProductName());
            }

            // Step 6: Chọn Cooling
            Cooling selectedCooling = selectCooling(criteria);
            if (selectedCooling != null) {
                build.setCooling(selectedCooling);
                log.info("Selected Cooling: {}", selectedCooling.getProduct().getProductName());
            }

            // Step 7: Chọn PSU
            PowerSupply selectedPsu = selectPowerSupply(criteria);
            if (selectedPsu != null) {
                build.setPowerSupply(selectedPsu);
                log.info("Selected PSU: {}", selectedPsu.getProduct().getProductName());
            }

            // Step 8: Chọn Case
            Case selectedCase = selectCase(criteria);
            if (selectedCase != null) {
                build.setPcCase(selectedCase);
                log.info("Selected Case: {}", selectedCase.getProduct().getProductName());
            }

        } catch (Exception e) {
            log.error("Error building PC: ", e);
        }

        return build;
    }

    /**
     * Chọn GPU dựa trên performance level và budget
     */
    private GPU selectGpu(AiPcBuildCriteria criteria) {
        List<GPU> allGpus = gpuRepository.findAll();

        if (allGpus.isEmpty()) return null;

        // Filter by budget
        if (criteria.getGpuBudget() != null) {
            double minPrice = criteria.getGpuBudget() * 0.7 * 1_000_000;
            double maxPrice = criteria.getGpuBudget() * 1.3 * 1_000_000;

            List<GPU> inBudget = allGpus.stream()
                .filter(gpu -> gpu.getProduct().getPrice() >= minPrice &&
                              gpu.getProduct().getPrice() <= maxPrice)
                .toList();

            if (!inBudget.isEmpty()) allGpus = inBudget;
        }

        // Select based on performance level
        if ("high".equalsIgnoreCase(criteria.getGpuPerformanceLevel())) {
            return allGpus.stream()
                .max(Comparator.comparing(gpu -> gpu.getProduct().getPrice()))
                .orElse(null);
        } else if ("low".equalsIgnoreCase(criteria.getGpuPerformanceLevel())) {
            return allGpus.stream()
                .min(Comparator.comparing(gpu -> gpu.getProduct().getPrice()))
                .orElse(null);
        } else {
            // Medium - chọn giữa
            return allGpus.stream()
                .sorted(Comparator.comparing(gpu -> gpu.getProduct().getPrice()))
                .skip(allGpus.size() / 2)
                .findFirst()
                .orElse(null);
        }
    }

    /**
     * Chọn CPU dựa trên performance level và budget
     */
    private CPU selectCpu(AiPcBuildCriteria criteria) {
        List<CPU> allCpus = cpuRepository.findAll();

        if (allCpus.isEmpty()) return null;

        // Filter by budget
        if (criteria.getCpuBudget() != null) {
            double minPrice = criteria.getCpuBudget() * 0.7 * 1_000_000;
            double maxPrice = criteria.getCpuBudget() * 1.3 * 1_000_000;

            List<CPU> inBudget = allCpus.stream()
                .filter(cpu -> cpu.getProduct().getPrice() >= minPrice &&
                              cpu.getProduct().getPrice() <= maxPrice)
                .toList();

            if (!inBudget.isEmpty()) allCpus = inBudget;
        }

        // Select based on performance level
        if ("high".equalsIgnoreCase(criteria.getCpuPerformanceLevel())) {
            return allCpus.stream()
                .max(Comparator.comparing(cpu -> cpu.getProduct().getPrice()))
                .orElse(null);
        } else if ("low".equalsIgnoreCase(criteria.getCpuPerformanceLevel())) {
            return allCpus.stream()
                .min(Comparator.comparing(cpu -> cpu.getProduct().getPrice()))
                .orElse(null);
        } else {
            return allCpus.stream()
                .sorted(Comparator.comparing(cpu -> cpu.getProduct().getPrice()))
                .skip(allCpus.size() / 2)
                .findFirst()
                .orElse(null);
        }
    }

    /**
     * Chọn Mainboard - logic đơn giản
     */
    private Mainboard selectMainboard(AiPcBuildCriteria criteria) {
        List<Mainboard> allMainboards = mainboardRepository.findAll();

        if (allMainboards.isEmpty()) return null;

        if (criteria.getMainboardBudget() != null) {
            double targetPrice = criteria.getMainboardBudget() * 1_000_000;
            return allMainboards.stream()
                .min(Comparator.comparing(mb -> Math.abs(mb.getProduct().getPrice() - targetPrice)))
                .orElse(null);
        }

        // Chọn mid-range
        return allMainboards.stream()
            .sorted(Comparator.comparing(mb -> mb.getProduct().getPrice()))
            .skip(allMainboards.size() / 2)
            .findFirst()
            .orElse(null);
    }

    /**
     * Chọn Memory
     */
    private Memory selectMemory(AiPcBuildCriteria criteria) {
        List<Memory> allMemory = memoryRepository.findAll();

        if (allMemory.isEmpty()) return null;

        // Filter by capacity
        if (criteria.getMinRamCapacity() != null) {
            List<Memory> filtered = allMemory.stream()
                .filter(mem -> mem.getCapacity() >= criteria.getMinRamCapacity())
                .toList();

            if (!filtered.isEmpty()) allMemory = filtered;
        }

        // Filter by type
        if (criteria.getRamType() != null) {
            List<Memory> filtered = allMemory.stream()
                .filter(mem -> mem.getType().contains(criteria.getRamType()))
                .toList();

            if (!filtered.isEmpty()) allMemory = filtered;
        }

        // Select by budget
        if (criteria.getRamBudget() != null) {
            double targetPrice = criteria.getRamBudget() * 1_000_000;
            return allMemory.stream()
                .min(Comparator.comparing(mem -> Math.abs(mem.getProduct().getPrice() - targetPrice)))
                .orElse(null);
        }

        return allMemory.stream().findFirst().orElse(null);
    }

    /**
     * Chọn Storage
     */
    private Storage selectStorage(AiPcBuildCriteria criteria) {
        List<Storage> allStorage = storageRepository.findAll();

        if (allStorage.isEmpty()) return null;

        // Filter by capacity
        if (criteria.getMinStorageCapacity() != null) {
            List<Storage> filtered = allStorage.stream()
                .filter(storage -> storage.getCapacity() >= criteria.getMinStorageCapacity())
                .toList();

            if (!filtered.isEmpty()) allStorage = filtered;
        }

        // Filter by type
        if (criteria.getStorageType() != null) {
            List<Storage> filtered = allStorage.stream()
                .filter(storage -> storage.getType().contains(criteria.getStorageType()))
                .toList();

            if (!filtered.isEmpty()) allStorage = filtered;
        }

        // Select by budget
        if (criteria.getStorageBudget() != null) {
            double targetPrice = criteria.getStorageBudget() * 1_000_000;
            return allStorage.stream()
                .min(Comparator.comparing(storage -> Math.abs(storage.getProduct().getPrice() - targetPrice)))
                .orElse(null);
        }

        return allStorage.stream().findFirst().orElse(null);
    }

    /**
     * Chọn Cooling - logic đơn giản
     */
    private Cooling selectCooling(AiPcBuildCriteria criteria) {
        List<Cooling> allCooling = coolingRepository.findAll();

        if (allCooling.isEmpty()) return null;

        if (criteria.getCoolingBudget() != null) {
            double targetPrice = criteria.getCoolingBudget() * 1_000_000;
            return allCooling.stream()
                .min(Comparator.comparing(cooling -> Math.abs(cooling.getProduct().getPrice() - targetPrice)))
                .orElse(null);
        }

        return allCooling.stream()
            .sorted(Comparator.comparing(cooling -> cooling.getProduct().getPrice()))
            .skip(allCooling.size() / 2)
            .findFirst()
            .orElse(null);
    }

    /**
     * Chọn PSU - dựa trên budget
     */
    private PowerSupply selectPowerSupply(AiPcBuildCriteria criteria) {
        List<PowerSupply> allPsu = powerSupplyRepository.findAll();

        if (allPsu.isEmpty()) return null;

        if (criteria.getPsuBudget() != null) {
            double targetPrice = criteria.getPsuBudget() * 1_000_000;
            return allPsu.stream()
                .min(Comparator.comparing(psu -> Math.abs(psu.getProduct().getPrice() - targetPrice)))
                .orElse(null);
        }

        return allPsu.stream()
            .sorted(Comparator.comparing(psu -> psu.getProduct().getPrice()))
            .skip(allPsu.size() / 2)
            .findFirst()
            .orElse(null);
    }

    /**
     * Chọn Case - logic đơn giản
     */
    private Case selectCase(AiPcBuildCriteria criteria) {
        List<Case> allCases = caseRepository.findAll();

        if (allCases.isEmpty()) return null;

        if (criteria.getCaseBudget() != null) {
            double targetPrice = criteria.getCaseBudget() * 1_000_000;
            return allCases.stream()
                .min(Comparator.comparing(c -> Math.abs(c.getProduct().getPrice() - targetPrice)))
                .orElse(null);
        }

        return allCases.stream()
            .sorted(Comparator.comparing(c -> c.getProduct().getPrice()))
            .skip(allCases.size() / 2)
            .findFirst()
            .orElse(null);
    }

    /**
     * Tính tổng giá build
     */
    public double calculateTotalPrice(BuildItemDto build) {
        double total = 0.0;

        if (build.getCpu() != null) total += build.getCpu().getProduct().getPrice();
        if (build.getGpu() != null) total += build.getGpu().getProduct().getPrice();
        if (build.getMainboard() != null) total += build.getMainboard().getProduct().getPrice();
        if (build.getMemory() != null) total += build.getMemory().getProduct().getPrice();
        if (build.getStorage() != null) total += build.getStorage().getProduct().getPrice();
        if (build.getPowerSupply() != null) total += build.getPowerSupply().getProduct().getPrice();
        if (build.getCooling() != null) total += build.getCooling().getProduct().getPrice();
        if (build.getPcCase() != null) total += build.getPcCase().getProduct().getPrice();

        return total;
    }
}

