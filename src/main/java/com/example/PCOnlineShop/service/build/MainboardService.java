package com.example.PCOnlineShop.service.build;
import com.example.PCOnlineShop.model.build.Mainboard;
import com.example.PCOnlineShop.repository.build.MainboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainboardService {
    private final MainboardRepository mainboardRepository;

    public MainboardService(MainboardRepository mainboardRepository) {
        this.mainboardRepository = mainboardRepository;
    }

    public List<Mainboard> getAllMainboards() {
        return mainboardRepository.findAll();
    }

    public Mainboard addMainboard(Mainboard mainboard) {
        return mainboardRepository.save(mainboard);
    }

    public Mainboard updateMainboard(Mainboard mainboard) {
        return mainboardRepository.save(mainboard);
    }

    public Mainboard getMainboardById(int id) {
        return mainboardRepository.findById(id).orElse(null);
    }

    public void deleteMainboard(int id) {
        mainboardRepository.deleteById(id);
    }


}
