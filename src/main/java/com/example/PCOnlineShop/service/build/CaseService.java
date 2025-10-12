package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Case;
import com.example.PCOnlineShop.repository.build.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private BuildService buildService;

    public List<Case> getAllCompatibleCases(BuildItemDto buildItem) {
        return buildService.getCompatibleCases(buildItem);
    }

    public List<Case> filterCasesByFormFactor(List<Case> cases, String formFactor) {
        if (formFactor == null || formFactor.isEmpty()) {
            return cases;
        }
        return cases.stream()
                .filter(c -> c.getFormFactor().equalsIgnoreCase(formFactor))
                .collect(Collectors.toList());
    }

    public Case selectCase(int caseId) {
        return caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
    }
}
