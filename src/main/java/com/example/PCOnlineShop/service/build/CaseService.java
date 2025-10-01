package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Case;
import com.example.PCOnlineShop.repository.build.CaseRepository;
import com.example.PCOnlineShop.repository.build.CpuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CaseService {
    private  final CaseRepository caseRepository;

    public List<Case> getCases()
    {
        return caseRepository.findAll();
    }

    public Case addCase(Case pcCase)
    {
        return caseRepository.save(pcCase);
    }

    public Case updateCase(Case pcCase)
    {
        return caseRepository.save(pcCase);
    }

    public Case getCaseById(int id)
    {
        return caseRepository.findById(id).orElse(null);
    }

    public void deleteCase(int id)
    {
        caseRepository.deleteById(id);
    }
}
