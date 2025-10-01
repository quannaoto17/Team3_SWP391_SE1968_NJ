package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage,Integer> {
}
