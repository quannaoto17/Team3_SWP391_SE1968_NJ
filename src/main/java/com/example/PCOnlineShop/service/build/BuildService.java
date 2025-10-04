package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildItemId;
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

    public List<CPU> getCompatibleCpus(int mainboardId) {
        Mainboard mainboard = mainboardRepository.findById(mainboardId).get();
        // This is a placeholder implementation. In a real scenario, you would check compatibility based on motherboard and CPU socket types.
        List<CPU> allCpus = cpuRepository.findAll();
        return allCpus.stream()
                .filter(cpu -> compatibilityService.checkMotherboardCpuCompatibility(mainboard, cpu)) // Replace null with actual motherboard
                .toList();
    }

    public List<GPU> getCompatibleGPUs(BuildItemId buildItemId) {
        BuildItem buildItem = new BuildItem();
        CPU cpu = cpuRepository.findById(buildItemId.getCpuId()).orElse(null);
        buildItem.setCpu(cpu);
        Mainboard mainboard = mainboardRepository.findById(buildItemId.getMainboardId()).orElse(null);
        buildItem.setMainboard(mainboard);
        // This is a placeholder implementation. In a real scenario, you would check compatibility based on
        List<GPU> allGpus = gpuRepository.findAll();
        return allGpus.stream()
                .filter(gpu -> compatibilityService.checkGpuCompatibility(buildItem, gpu)) // Replace null with actual build item
                .toList();
    }
}
