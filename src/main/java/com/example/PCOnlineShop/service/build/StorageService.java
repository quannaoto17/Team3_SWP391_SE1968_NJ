package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.PowerSupply;
import com.example.PCOnlineShop.repository.build.PowerSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StorageService {
    private PowerSupplyRepository powerSupplyRepository;

    public List<PowerSupply> getAllPowerSupply() {
        return powerSupplyRepository.findAll();
    }

    public PowerSupply addPowerSupply(PowerSupply powerSupply) {
        return powerSupplyRepository.save(powerSupply);
    }

    public PowerSupply updatePowerSupply(PowerSupply powerSupply) {
        return powerSupplyRepository.save(powerSupply);
    }

    public PowerSupply getPowerSupplyById(int id) {
        return powerSupplyRepository.findById(id).orElse(null);
    }

    public void deletePowerSupply(int id) {
        powerSupplyRepository.deleteById(id);
    }
}
