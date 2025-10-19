package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Integer> {
    Optional <Memory> findByProduct_ProductId(int id);
}
