package com.example.PCOnlineShop.service.build;
import com.example.PCOnlineShop.model.build.Motherboard;
import com.example.PCOnlineShop.repository.build.MotherboardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MotherboardService {
    private final MotherboardRepository motherboardRepository;

    public List<Motherboard> getAllMotherboards() {
        return motherboardRepository.findAll();
    }

    public Motherboard addMotherboard(Motherboard motherboard) {
        return motherboardRepository.save(motherboard);
    }

    public Motherboard updateMotherboard(Motherboard motherboard) {
        return motherboardRepository.save(motherboard);
    }

    public Motherboard getMotherboardById(int id) {
        return motherboardRepository.findById(id).orElse(null);
    }

    public void deleteMotherboard(int id) {
        motherboardRepository.deleteById(id);
    }


}
