package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.CPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CpuRepository extends JpaRepository<CPU, Integer> {
    Optional<CPU> findByProductId(Integer productId); // ✅ đúng tên field
}
