package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.GPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository<GPU, Integer> {
}
