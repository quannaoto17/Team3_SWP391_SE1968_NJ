package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.GPU;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.GpuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class GpuService {
    private  GpuRepository gpuRepository;

    public List<GPU> getAllGpu() {
        return gpuRepository.findAllWithImages();
    }

    public GPU addGpu(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public GPU updateGpu(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public GPU getGpuById(int id) {
        return gpuRepository.findByIdWithImages(id).orElse(null);
    }

    public void deleteGpu(int id) {
        gpuRepository.deleteById(id);
    }

    public List<GPU> filterGpus(List<GPU> gpus, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            gpus = gpus.stream()
                    .filter(mb -> filters.get("brands").contains(mb.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    gpus = gpus.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    gpus = gpus.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    gpus = gpus.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    gpus = gpus.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return gpus;
    }

    public List<Brand> getAllBrands(List<GPU> gpus) {
        return gpus.stream()
                .map(gpu -> gpu.getProduct().getBrand())
                .distinct()
                .toList();
    }
}
