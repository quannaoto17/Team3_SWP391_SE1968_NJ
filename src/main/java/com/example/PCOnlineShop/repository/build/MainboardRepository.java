package com.example.PCOnlineShop.repository.build;
import com.example.PCOnlineShop.model.build.Mainboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MainboardRepository extends JpaRepository<Mainboard, Integer> {
}
