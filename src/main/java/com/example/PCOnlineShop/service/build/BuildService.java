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
}
