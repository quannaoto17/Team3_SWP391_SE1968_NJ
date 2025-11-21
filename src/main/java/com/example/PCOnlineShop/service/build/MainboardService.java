package com.example.PCOnlineShop.service.build;
import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Mainboard;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.MainboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainboardService {
    private final MainboardRepository mainboardRepository;
    private final BuildService buildService;


    @Transactional(readOnly = true)
    public List<Mainboard> getAllMainboards() {
        return mainboardRepository.findAllWithImages();
    }

    public Mainboard addMainboard(Mainboard mainboard) {
        return mainboardRepository.save(mainboard);
    }

    public Mainboard updateMainboard(Mainboard mainboard) {
        return mainboardRepository.save(mainboard);
    }

    @Transactional(readOnly = true)
    public Mainboard getMainboardById(int id) {
        return mainboardRepository.findByIdWithImages(id).orElse(null);
    }

    public void deleteMainboard(int id) {
        mainboardRepository.deleteById(id);
    }

    public List<Mainboard> filterMainboards(List<Mainboard> mainboards, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            mainboards = mainboards.stream()
                    .filter(mb -> filters.get("brands").contains(mb.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    mainboards = mainboards.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    mainboards = mainboards.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    mainboards = mainboards.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    mainboards = mainboards.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return mainboards;
    }

    public List<Brand> getAllBrands(BuildItemDto buildItem) {
        List<Mainboard> mainboards =buildService.getCompatibleMainboards(buildItem);
        return mainboards.stream()
                .map(mb -> mb.getProduct().getBrand())
                .distinct()
                .toList();
    }
}
