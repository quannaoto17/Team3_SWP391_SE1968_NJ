package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service để tự động map các spec values sang categories
 * Ví dụ: GPU có memoryType="GDDR6" -> thêm category "GDDR6"
 *        CPU có socket="AM4" -> thêm category "Socket AM4"
 */
@Service
@RequiredArgsConstructor
public class SpecToCategoryMappingService {

    private final CategoryRepository categoryRepository;

    /**
     * Map spec values của một component sang categories
     * Trả về Set<Category> để tránh trùng lặp
     */
    public Set<Category> mapComponentSpecsToCategories(Product product, int primaryCategoryId, Object component) {
        Set<Category> categories = new HashSet<>();

        // Luôn thêm primary category (loại linh kiện)
        categoryRepository.findById(primaryCategoryId).ifPresent(categories::add);

        switch (primaryCategoryId) {
            case 1 -> mapMainboardSpecs((Mainboard) component, categories);
            case 2 -> mapCpuSpecs((CPU) component, categories);
            case 3 -> mapGpuSpecs((GPU) component, categories);
            case 4 -> mapMemorySpecs((Memory) component, categories);
            case 5 -> mapStorageSpecs((Storage) component, categories);
            case 6 -> mapCaseSpecs((Case) component, categories);
            case 7 -> mapPsuSpecs((PowerSupply) component, categories);
            case 8 -> mapCoolingSpecs((Cooling) component, categories);
        }

        return categories;
    }

    // ================ MAINBOARD MAPPING ================
    private void mapMainboardSpecs(Mainboard mb, Set<Category> categories) {
        // Socket
        if (mb.getSocket() != null && !mb.getSocket().isEmpty()) {
            findCategoryByName("Socket " + mb.getSocket()).ifPresent(categories::add);
        }

        // Form Factor
        if (mb.getFormFactor() != null && !mb.getFormFactor().isEmpty()) {
            findCategoryByName(mb.getFormFactor()).ifPresent(categories::add);
        }

        // Memory Type
        if (mb.getMemoryType() != null && !mb.getMemoryType().isEmpty()) {
            findCategoryByName(mb.getMemoryType()).ifPresent(categories::add);
        }

        // PCIe Version
        if (mb.getPcieVersion() != null && !mb.getPcieVersion().isEmpty()) {
            findCategoryByName("PCIe " + mb.getPcieVersion()).ifPresent(categories::add);
        }
    }

    // ================ CPU MAPPING ================
    private void mapCpuSpecs(CPU cpu, Set<Category> categories) {
        // Socket
        if (cpu.getSocket() != null && !cpu.getSocket().isEmpty()) {
            findCategoryByName("Socket " + cpu.getSocket()).ifPresent(categories::add);
        }

        // PCIe Version
        if (cpu.getPcieVersion() != null && !cpu.getPcieVersion().isEmpty()) {
            findCategoryByName("PCIe " + cpu.getPcieVersion()).ifPresent(categories::add);
        }
    }

    // ================ GPU MAPPING ================
    private void mapGpuSpecs(GPU gpu, Set<Category> categories) {
        // Memory Type
        if (gpu.getMemoryType() != null && !gpu.getMemoryType().isEmpty()) {
            findCategoryByName(gpu.getMemoryType()).ifPresent(categories::add);
        }

        // PCIe Version
        if (gpu.getPcieVersion() != null && !gpu.getPcieVersion().isEmpty()) {
            findCategoryByName("PCIe " + gpu.getPcieVersion()).ifPresent(categories::add);
        }
    }

    // ================ MEMORY MAPPING ================
    private void mapMemorySpecs(Memory memory, Set<Category> categories) {
        // Memory Type
        if (memory.getType() != null && !memory.getType().isEmpty()) {
            findCategoryByName(memory.getType()).ifPresent(categories::add);
        }
    }

    // ================ STORAGE MAPPING ================
    private void mapStorageSpecs(Storage storage, Set<Category> categories) {
        // Interface Type
        if (storage.getInterfaceType() != null && !storage.getInterfaceType().isEmpty()) {
            // Map "NVMe" -> "M.2 NVMe", "SATA" -> "SATA", "M.2" -> có thể là M.2 SATA hoặc NVMe
            if (storage.getInterfaceType().equalsIgnoreCase("NVMe")) {
                findCategoryByName("M.2 NVMe").ifPresent(categories::add);
            } else if (storage.getInterfaceType().equalsIgnoreCase("SATA")) {
                findCategoryByName("SATA").ifPresent(categories::add);
            } else if (storage.getInterfaceType().equalsIgnoreCase("M.2")) {
                // Nếu type là NVMe thì M.2 NVMe, ngược lại M.2 SATA
                if (storage.getType() != null && storage.getType().equalsIgnoreCase("NVMe")) {
                    findCategoryByName("M.2 NVMe").ifPresent(categories::add);
                } else {
                    findCategoryByName("M.2 SATA").ifPresent(categories::add);
                }
            }
        }
    }

    // ================ CASE MAPPING ================
    private void mapCaseSpecs(Case pcCase, Set<Category> categories) {
        // Form Factor
        if (pcCase.getFormFactor() != null && !pcCase.getFormFactor().isEmpty()) {
            findCategoryByName(pcCase.getFormFactor()).ifPresent(categories::add);
        }

        // PSU Form Factor
        if (pcCase.getPsuFormFactor() != null && !pcCase.getPsuFormFactor().isEmpty()) {
            findCategoryByName(pcCase.getPsuFormFactor() + " PSU").ifPresent(categories::add);
        }
    }

    // ================ PSU MAPPING ================
    private void mapPsuSpecs(PowerSupply psu, Set<Category> categories) {
        // Form Factor
        if (psu.getFormFactor() != null && !psu.getFormFactor().isEmpty()) {
            findCategoryByName(psu.getFormFactor() + " PSU").ifPresent(categories::add);
        }
    }

    // ================ COOLING MAPPING ================
    private void mapCoolingSpecs(Cooling cooling, Set<Category> categories) {
        // Cooling Type
        if (cooling.getType() != null && !cooling.getType().isEmpty()) {
            if (cooling.getType().equalsIgnoreCase("Air")) {
                findCategoryByName("Air Cooling").ifPresent(categories::add);
            } else if (cooling.getType().equalsIgnoreCase("AIO")) {
                findCategoryByName("AIO Cooling").ifPresent(categories::add);
            }
        }
    }

    // ================ HELPER METHOD ================
    /**
     * Tìm category theo tên (case-insensitive)
     */
    private Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findAll().stream()
                .filter(cat -> cat.getCategoryName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Lấy tất cả categories từ một product và component specs của nó
     */
    public List<Category> getAllCategoriesForProduct(Product product, int primaryCategoryId, Object component) {
        Set<Category> categories = mapComponentSpecsToCategories(product, primaryCategoryId, component);
        return new ArrayList<>(categories);
    }
}

