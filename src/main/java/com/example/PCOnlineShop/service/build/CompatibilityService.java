package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.CPU;
import com.example.PCOnlineShop.model.build.Motherboard;
import org.springframework.stereotype.Service;

@Service
public class CompatibilityService {
    public boolean checkMotherboardCpuCompatibility(Motherboard motherboard, CPU cpu) {
        if (!motherboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }
        // Add more compatibility checks as needed
        return true; // Placeholder
    }
}
