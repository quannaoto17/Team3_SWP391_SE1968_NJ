package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Memory;
import com.example.PCOnlineShop.repository.build.MemoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MemoryService {
    private MemoryRepository memoryRepository;

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
}
