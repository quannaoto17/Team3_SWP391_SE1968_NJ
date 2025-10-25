package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Case;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.build.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    public List<Case> getAllCases() {
        return caseRepository.findAll();
    }

    public Case addCase(Case pcCase) {
        return caseRepository.save(pcCase);
    }

    public Case updateCase(Case pcCase) {
        return caseRepository.save(pcCase);
    }

    public Case getCaseById(int id) {
        return caseRepository.findById(id).orElse(null);
    }

    public void deleteCase(int id) {
        caseRepository.deleteById(id);
    }

    public List<Case> filterCases(List<Case> cases, Map<String,List<String>> filters, String sortBy) {
        // Filter by brands if selected
        if (filters.get("brands") != null && !filters.get("brands").isEmpty()) {
            cases = cases.stream()
                    .filter(c -> filters.get("brands").contains(c.getProduct().getBrand().getName()))
                    .toList();
        }

        // Sort if specified
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    cases = cases.stream()
                            .sorted((a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()))
                            .toList();
                    break;
                case "priceDesc":
                    cases = cases.stream()
                            .sorted((a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()))
                            .toList();
                    break;
                case "nameAsc":
                    cases = cases.stream()
                            .sorted((a, b) -> a.getProduct().getProductName().compareTo(b.getProduct().getProductName()))
                            .toList();
                    break;
                case "nameDesc":
                    cases = cases.stream()
                            .sorted((a, b) -> b.getProduct().getProductName().compareTo(a.getProduct().getProductName()))
                            .toList();
                    break;
            }
        }
        return cases;
    }

    public List<Brand> getAllBrands(List<Case> cases) {
        return cases.stream()
                .map(c -> c.getProduct().getBrand())
                .distinct()
                .toList();
    }
}
