package com.example.PCOnlineShop.service.build;
import com.example.PCOnlineShop.model.build.CPU;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.CpuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CpuService {
    private final CpuRepository cpuRepository;

    public List<CPU> getAllCpus() {
        return cpuRepository.findAllWithImages();
    }

    public CPU addCpu(CPU cpu) {
        return cpuRepository.save(cpu);
    }

    public CPU updateCpu(CPU cpu) {
        return cpuRepository.save(cpu);
    }

    public CPU getCpuById(int id) {
        return cpuRepository.findByIdWithImages(id).orElse(null);
    }

    public void deleteCpu(int id) {
        cpuRepository.deleteById(id);
    }

    public List<CPU> filterCpus(List<CPU> cpus, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            cpus = cpus.stream()
                    .filter(mb -> filters.get("brands").contains(mb.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    cpus = cpus.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    cpus = cpus.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    cpus = cpus.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    cpus = cpus.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return cpus;
    }

    public List<Brand> getAllBrands(List<CPU> cpus) {
        return cpus.stream()
                .map(cpu -> cpu.getProduct().getBrand())
                .distinct()
                .toList();
    }
}
