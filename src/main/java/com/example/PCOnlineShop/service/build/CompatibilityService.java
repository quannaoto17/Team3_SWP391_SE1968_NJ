package com.example.PCOnlineShop.service.build;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompatibilityService {


    public boolean checkMotherboardCpuCompatibility(int motherboardId, int cpuId) {
        return true; // Placeholder
    }
}
