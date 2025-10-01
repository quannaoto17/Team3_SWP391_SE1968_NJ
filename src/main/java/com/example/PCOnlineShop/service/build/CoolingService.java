package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Cooling;
import com.example.PCOnlineShop.repository.build.CoolingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service

public class CoolingService {
    private CoolingRepository coolingRepository;

    public List<Cooling> getCoolings()
    {
        return coolingRepository.findAll();
    }

    public Cooling addCooling(Cooling cooling)
    {
        return coolingRepository.save(cooling);
    }

    public Cooling updateCooling(Cooling cooling) {
        return coolingRepository.save(cooling);
    }

    public Cooling getCoolingById(int id) {
        return coolingRepository.findById(id).orElse(null);
    }

    public void deleteCooling(int id) {
        coolingRepository.deleteById(id);
    }
}
