package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.PowerSupply;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.PowerSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PowerSupplyService {
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

    public List<PowerSupply> filterPowerSupplies(List<PowerSupply> powerSupplies, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            powerSupplies = powerSupplies.stream()
                    .filter(p -> filters.get("brands").contains(p.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    powerSupplies = powerSupplies.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    powerSupplies = powerSupplies.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    powerSupplies = powerSupplies.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    powerSupplies = powerSupplies.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return powerSupplies;
    }

    public List<Brand> getAllBrands(List<PowerSupply> powerSupplies) {
        return powerSupplies.stream()
                .map(psu -> psu.getProduct().getBrand())
                .distinct()
                .toList();
    }
}
