package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.CPU;
import com.example.PCOnlineShop.model.build.Mainboard;
import com.example.PCOnlineShop.repository.build.CpuRepository;
import com.example.PCOnlineShop.repository.build.MainboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class BuildService {
    private final CompatibilityService compatibilityService;
    private final MainboardRepository mainboardRepository;
    private final CpuRepository cpuRepository;

    public List<CPU> getCompatibleCpus(int mainboardId) {
        Mainboard mainboard = mainboardRepository.findById(mainboardId).get();
        // This is a placeholder implementation. In a real scenario, you would check compatibility based on motherboard and CPU socket types.
        List<CPU> allCpus = cpuRepository.findAll();
        return allCpus.stream()
                .filter(cpu -> compatibilityService.checkMotherboardCpuCompatibility(mainboard, cpu)) // Replace null with actual motherboard
                .toList();
    }
}
