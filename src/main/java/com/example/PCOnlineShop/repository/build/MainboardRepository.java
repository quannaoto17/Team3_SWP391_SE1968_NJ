package com.example.PCOnlineShop.repository.build;
import com.example.PCOnlineShop.model.build.Mainboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MainboardRepository extends JpaRepository<Mainboard, Integer> {
    Optional <Mainboard> findByProduct_ProductId(int id);
}
