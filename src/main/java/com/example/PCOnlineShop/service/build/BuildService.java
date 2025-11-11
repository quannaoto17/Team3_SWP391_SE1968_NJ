package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BuildService {
    private final CompatibilityService compatibilityService;
    private final MainboardRepository mainboardRepository;
    private final GpuRepository gpuRepository;
    private final CpuRepository cpuRepository;
    private final CaseRepository caseRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoolingRepository coolingRepository;
    private final ProductRepository productRepository;

    /**
     * Get compatible mainboards based on current build
     * Returns all mainboards if build is empty, otherwise filters by compatibility
     */
    public List<Mainboard> getCompatibleMainboards(BuildItemDto buildItem) {
        List<Mainboard> allMainboards = mainboardRepository.findAll();

        if (buildItem.isEmpty()) {
            log.debug("Build is empty, returning all {} mainboards", allMainboards.size());
            return sortByPerformanceAndPrice(allMainboards);
        }

        List<Mainboard> compatible = allMainboards.stream()
                .filter(mainboard -> compatibilityService.checkMainboardCompatibility(buildItem, mainboard))
                .collect(Collectors.toList());

        log.debug("Found {} compatible mainboards out of {}", compatible.size(), allMainboards.size());
        return sortByPerformanceAndPrice(compatible);
    }

    /**
     * Get compatible CPUs based on mainboard socket
     */
    public List<CPU> getCompatibleCpus(BuildItemDto buildItem) {
        List<CPU> allCpus = cpuRepository.findAll();

        if (buildItem.getMainboard() == null) {
            log.debug("No mainboard selected, returning all {} CPUs", allCpus.size());
            return sortByPerformanceAndPrice(allCpus);
        }

        List<CPU> compatible = allCpus.stream()
                .filter(cpu -> compatibilityService.checkCpuCompatibility(buildItem, cpu))
                .collect(Collectors.toList());

        log.debug("Found {} compatible CPUs for socket {}",
                compatible.size(),
                buildItem.getMainboard().getSocket());
        return sortByPerformanceAndPrice(compatible);
    }

    /**
     * Get compatible GPUs - all GPUs are compatible, sorted by performance
     */
    public List<GPU> getCompatibleGPUs(BuildItemDto buildItem) {
        List<GPU> allGpus = gpuRepository.findAll();

        List<GPU> compatible = allGpus.stream()
                .filter(gpu -> compatibilityService.checkGpuCompatibility(buildItem, gpu))
                .collect(Collectors.toList());

        log.debug("Found {} compatible GPUs", compatible.size());
        return sortByPerformanceAndPrice(compatible);
    }

    /**
     * Get compatible cases based on form factor, GPU length, and cooler height
     */
    public List<Case> getCompatibleCases(BuildItemDto buildItem) {
        List<Case> allCases = caseRepository.findAll();

        if (buildItem.isEmpty()) {
            log.debug("Build is empty, returning all {} cases", allCases.size());
            return sortByPrice(allCases);
        }

        List<Case> compatible = allCases.stream()
                .filter(pcCase -> compatibilityService.checkCaseCompatibility(buildItem, pcCase))
                .collect(Collectors.toList());

        log.debug("Found {} compatible cases out of {}", compatible.size(), allCases.size());
        return sortByPrice(compatible);
    }

    /**
     * Get compatible memory based on mainboard's memory type
     */
    public List<Memory> getCompatibleMemory(BuildItemDto buildItem) {
        List<Memory> allMemory = memoryRepository.findAll();

        if (buildItem.getMainboard() == null) {
            log.debug("No mainboard selected, returning all {} memory", allMemory.size());
            return sortByPerformanceAndPrice(allMemory);
        }

        List<Memory> compatible = allMemory.stream()
                .filter(memory -> compatibilityService.checkMemoryCompatibility(buildItem, memory))
                .collect(Collectors.toList());

        log.debug("Found {} compatible memory for type {}",
                compatible.size(),
                buildItem.getMainboard().getMemoryType());
        return sortByPerformanceAndPrice(compatible);
    }

    /**
     * Get compatible storage - all storage is compatible
     */
    public List<Storage> getCompatibleStorage(BuildItemDto buildItem) {
        List<Storage> allStorage = storageRepository.findAll();

        List<Storage> compatible = allStorage.stream()
                .filter(storage -> compatibilityService.checkStorageCompatibility(buildItem, storage))
                .collect(Collectors.toList());

        log.debug("Found {} compatible storage devices", compatible.size());
        return sortByPerformanceAndPrice(compatible);
    }

    /**
     * Get compatible power supplies based on required wattage and form factor
     */
    public List<PowerSupply> getCompatiblePowerSupplies(BuildItemDto buildItem) {
        List<PowerSupply> allPowerSupplies = powerSupplyRepository.findAll();

        if (buildItem.isEmpty()) {
            log.debug("Build is empty, returning all {} PSUs", allPowerSupplies.size());
            return sortByWattageAndPrice(allPowerSupplies);
        }

        List<PowerSupply> compatible = allPowerSupplies.stream()
                .filter(psu -> compatibilityService.checkPowerSupplyCompatibility(buildItem, psu))
                .collect(Collectors.toList());

        log.debug("Found {} compatible PSUs", compatible.size());
        return sortByWattageAndPrice(compatible);
    }

    /**
     * Get compatible cooling based on CPU TDP and case cooler height
     */
    public List<Cooling> getCompatibleCoolings(BuildItemDto buildItem) {
        List<Cooling> allCoolings = coolingRepository.findAll();

        if (buildItem.getCpu() == null) {
            log.debug("No CPU selected, returning all {} cooling solutions", allCoolings.size());
            return sortByTdpAndPrice(allCoolings);
        }

        List<Cooling> compatible = allCoolings.stream()
                .filter(cooling -> compatibilityService.checkCoolingCompatibility(buildItem, cooling))
                .collect(Collectors.toList());

        log.debug("Found {} compatible cooling solutions for TDP {}",
                compatible.size(),
                buildItem.getCpu().getTdp());
        return sortByTdpAndPrice(compatible);
    }

    // Helper methods for sorting

    private <T> List<T> sortByPerformanceAndPrice(List<T> items) {
        return items.stream()
                .sorted(Comparator
                        .comparing((T item) -> getPerformanceScore(item), Comparator.reverseOrder())
                        .thenComparing(this::getPrice))
                .collect(Collectors.toList());
    }

    private <T> List<T> sortByPrice(List<T> items) {
        return items.stream()
                .sorted(Comparator.comparing(this::getPrice))
                .collect(Collectors.toList());
    }

    private List<PowerSupply> sortByWattageAndPrice(List<PowerSupply> items) {
        return items.stream()
                .sorted(Comparator
                        .comparing(PowerSupply::getWattage, Comparator.reverseOrder())
                        .thenComparing(this::getPrice))
                .collect(Collectors.toList());
    }

    private List<Cooling> sortByTdpAndPrice(List<Cooling> items) {
        return items.stream()
                .sorted(Comparator
                        .comparing(Cooling::getTdp, Comparator.reverseOrder())
                        .thenComparing(this::getPrice))
                .collect(Collectors.toList());
    }

    private <T> Integer getPerformanceScore(T item) {
        if (item instanceof Mainboard) {
            return ((Mainboard) item).getProduct().getPerformanceScore() != null
                    ? ((Mainboard) item).getProduct().getPerformanceScore() : 0;
        } else if (item instanceof CPU) {
            return ((CPU) item).getProduct().getPerformanceScore() != null
                    ? ((CPU) item).getProduct().getPerformanceScore() : 0;
        } else if (item instanceof GPU) {
            return ((GPU) item).getProduct().getPerformanceScore() != null
                    ? ((GPU) item).getProduct().getPerformanceScore() : 0;
        } else if (item instanceof Memory) {
            return ((Memory) item).getProduct().getPerformanceScore() != null
                    ? ((Memory) item).getProduct().getPerformanceScore() : 0;
        } else if (item instanceof Storage) {
            return ((Storage) item).getProduct().getPerformanceScore() != null
                    ? ((Storage) item).getProduct().getPerformanceScore() : 0;
        }
        return 0;
    }

    private <T> Double getPrice(T item) {
        if (item instanceof Mainboard) {
            return ((Mainboard) item).getProduct().getPrice();
        } else if (item instanceof CPU) {
            return ((CPU) item).getProduct().getPrice();
        } else if (item instanceof GPU) {
            return ((GPU) item).getProduct().getPrice();
        } else if (item instanceof Memory) {
            return ((Memory) item).getProduct().getPrice();
        } else if (item instanceof Storage) {
            return ((Storage) item).getProduct().getPrice();
        } else if (item instanceof Case) {
            return ((Case) item).getProduct().getPrice();
        } else if (item instanceof PowerSupply) {
            return ((PowerSupply) item).getProduct().getPrice();
        } else if (item instanceof Cooling) {
            return ((Cooling) item).getProduct().getPrice();
        }
        return 0.0;
    }

    // Other (generic product)
    public List<Product> getOtherProducts() {
        // For now, return empty list as we focus on main components
        // Can be expanded later to include accessories, peripherals, etc.
        return List.of();
    }

    public Optional<Other> findOtherByProductId(Integer productId) {
        Optional<Product> opt = productRepository.findById(productId);
        if (opt.isEmpty()) return Optional.empty();
        Product p = opt.get();
        Other o = new Other();
        o.setName(p.getProductName());
        o.setBrand(p.getBrand() != null ? p.getBrand().getName() : null);
        o.setType("Other");
        o.setDescription(p.getDescription());
        o.setPrice(p.getPrice());
        return Optional.of(o);
    }
}
