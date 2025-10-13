package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.model.build.Storage;
import com.example.PCOnlineShop.repository.build.StorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StorageService {
    private StorageRepository storageRepository;

    public Storage getStorageById(int id) {
        return storageRepository.findById(id).orElse(null);
    }

    public List<Storage> getAllStorages() {
        return storageRepository.findAll();
    }


    public Storage addStorage(Storage storage) {
        return storageRepository.save(storage);
    }

    public Storage updateStorage(Storage storage) {
        return storageRepository.save(storage);
    }

    public void deleteStorage(int id) {
        storageRepository.deleteById(id);
    }
}
