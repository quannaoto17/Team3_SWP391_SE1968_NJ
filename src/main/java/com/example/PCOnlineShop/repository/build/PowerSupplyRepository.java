package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.PowerSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupply,Integer> {
}
