package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Cooling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoolingRepository extends JpaRepository<Cooling,Integer> {
   Optional <Cooling> findByProduct_ProductId(int id);
}
