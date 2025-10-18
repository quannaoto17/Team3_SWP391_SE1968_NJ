package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Cooling;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.CoolingRepository;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class CoolingService {
    private CoolingRepository coolingRepository;
    private final BrandRepository brandRepository;

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

    public List<Cooling> filterCoolings(List<Cooling> coolings, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            coolings = coolings.stream()
                    .filter(c -> filters.get("brands").contains(c.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    coolings = coolings.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    coolings = coolings.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    coolings = coolings.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    coolings = coolings.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return coolings;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
