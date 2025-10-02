package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.CPU;
import com.example.PCOnlineShop.model.build.Mainboard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompatibilityService {
    public boolean checkMotherboardCpuCompatibility(Mainboard mainboard, CPU cpu) {
        if (!mainboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }
        // Add more compatibility checks as needed
        return true; // Placeholder
    }
}
