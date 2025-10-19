package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.PowerSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupply,Integer> {
    Optional<PowerSupply> findByProduct_ProductId(int id);
}
