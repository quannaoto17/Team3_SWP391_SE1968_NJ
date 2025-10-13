package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.build.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class BuildService {
    private final CompatibilityService compatibilityService;
    private final MainboardRepository mainboardRepository;
    private final GpuRepository gpuRepository;
    private final CpuRepository cpuRepository;
    private final CaseRepository caseRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoolingRepository coolingRepository;

    public  List<Mainboard> getCompatibleMainboards(BuildItemDto buildItem) {
        // This is a placeholder implementation. In a real scenario, you would check compatibility based on CPU socket types.
        List<Mainboard> allMainboards = mainboardRepository.findAll();
        if (buildItem.isEmpty()) return allMainboards;
        return allMainboards.stream()
                .filter(mainboard -> compatibilityService.checkMainboardCompatibility(buildItem, mainboard )) // Replace null with actual CPU
                .toList();
    }

    public List<CPU> getCompatibleCpus(BuildItemDto buildItem) {
        // This is a placeholder implementation. In a real scenario, you would check compatibility based on motherboard and CPU socket types.
        List<CPU> allCpus = cpuRepository.findAll();
        return allCpus.stream()
                .filter(cpu -> compatibilityService.checkCpuCompatibility(buildItem, cpu)) // Replace null with actual motherboard
                .toList();
    }

    public List<GPU> getCompatibleGPUs(BuildItemDto buildItem) {
        // This is a placeholder implementation. In a real scenario, you would check compatibility based on
        List<GPU> allGpus = gpuRepository.findAll();
        return allGpus.stream()
                .filter(gpu -> compatibilityService.checkGpuCompatibility(buildItem, gpu)) // Replace null with actual build item
                .toList();
    }

    public List<Case> getCompatibleCases(BuildItemDto buildItem) {
        List<Case> allCases = caseRepository.findAll();
        return allCases.stream()
                .filter(pcCase -> compatibilityService.checkCaseCompatibility(buildItem, pcCase))
                .toList();
    }

    public List<Memory> getCompatibleMemory(BuildItemDto buildItem) {
        List<Memory> allMemory = memoryRepository.findAll();
        return allMemory.stream()
                .filter(memory -> compatibilityService.checkMemoryCompatibility(buildItem, memory))
                .toList();
    }

    public List<Storage> getCompatibleStorage(BuildItemDto buildItem) {
        List<Storage> allStorage = storageRepository.findAll();
        return allStorage.stream()
                .filter(storage -> compatibilityService.checkStorageCompatibility(buildItem, storage))
                .toList();
    }

    public List<PowerSupply> getCompatiblePowerSupplies(BuildItemDto buildItem) {
        List<PowerSupply> allPowerSupplies = powerSupplyRepository.findAll();
        return allPowerSupplies.stream()
                .filter(psu -> compatibilityService.checkPowerSupplyCompatibility(buildItem, psu))
                .toList();
    }

    public List<Cooling> getCompatibleCoolings(BuildItemDto buildItem) {
        List<Cooling> allCoolings = coolingRepository.findAll();
        return allCoolings.stream()
                .filter(cooling -> compatibilityService.checkCoolingCompatibility(buildItem, cooling))
                .toList();
    }
}
