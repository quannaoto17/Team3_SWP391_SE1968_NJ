package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Memory;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.MemoryRepository;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class MemoryService {
    private MemoryRepository memoryRepository;
    private final BrandRepository brandRepository;

    public List<Memory> getAllMemory() {
        return memoryRepository.findAll();
    }

    public Memory addMemory(Memory memory) {
        return memoryRepository.save(memory);
    }

    public Memory updateMemory(Memory memory) {
        return memoryRepository.save(memory);
    }

    public Memory getMemoryById(int id) {
        return memoryRepository.findById(id).orElse(null);
    }

    public void deleteMemory(int id) {
        memoryRepository.deleteById(id);
    }

    public List<Memory> filterMemories(List<Memory> memories, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            memories = memories.stream()
                    .filter(m -> filters.get("brands").contains(m.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    memories = memories.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    memories = memories.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    memories = memories.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    memories = memories.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return memories;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
