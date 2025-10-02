package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Integer> {
}
