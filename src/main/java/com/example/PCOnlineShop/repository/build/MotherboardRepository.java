package com.example.PCOnlineShop.repository.build;
import com.example.PCOnlineShop.model.build.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Integer> {
}
