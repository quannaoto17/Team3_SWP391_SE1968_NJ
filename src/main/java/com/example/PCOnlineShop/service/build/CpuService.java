package com.example.PCOnlineShop.service.build;
import com.example.PCOnlineShop.model.build.CPU;
import com.example.PCOnlineShop.repository.build.CpuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CpuService {
    private final CpuRepository cpuRepository;

    public CpuService(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    public List<CPU> getAllCpus() {
        return cpuRepository.findAll();
    }

    public CPU addCpu(CPU cpu) {
        return cpuRepository.save(cpu);
    }

    public CPU updateCpu(CPU cpu) {
        return cpuRepository.save(cpu);
    }

    public CPU getCpuById(int id) {
        return cpuRepository.findById(id).orElse(null);
    }

    public void deleteCpu(int id) {
        cpuRepository.deleteById(id);
    }
}
