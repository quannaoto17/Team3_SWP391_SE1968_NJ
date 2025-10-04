package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.GPU;
import com.example.PCOnlineShop.repository.build.GpuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GpuService {
    private  GpuRepository gpuRepository;

    public List<GPU> getAllGpu() {
        return gpuRepository.findAll();
    }

    public GPU addGpu(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public GPU updateGpu(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public GPU getGpuById(int id) {
        return gpuRepository.findById(id).orElse(null);
    }

    public void deleteGpu(int id) {
        gpuRepository.deleteById(id);
    }
}
