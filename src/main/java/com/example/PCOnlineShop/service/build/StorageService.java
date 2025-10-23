package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Storage;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.StorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StorageService {
    private StorageRepository storageRepository;

    public Storage getStorageById(int id) {
        return storageRepository.findById(id).orElse(null);
    }

    public List<Storage> getAllStorages() {
        return storageRepository.findAll();
    }


    public Storage addStorage(Storage storage) {
        return storageRepository.save(storage);
    }

    public Storage updateStorage(Storage storage) {
        return storageRepository.save(storage);
    }

    public void deleteStorage(int id) {
        storageRepository.deleteById(id);
    }

    public List<Storage> filterStorages(List<Storage> storages, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            storages = storages.stream()
                    .filter(s -> filters.get("brands").contains(s.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    storages = storages.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    storages = storages.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    storages = storages.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    storages = storages.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return storages;
    }

    public List<Brand> getAllBrands(List<Storage> storages) {
        return storages.stream()
                .map(storage -> storage.getProduct().getBrand())
                .distinct()
                .toList();
    }
}
