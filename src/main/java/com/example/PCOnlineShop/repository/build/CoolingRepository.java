package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Cooling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoolingRepository extends JpaRepository<Cooling,Integer> {
    Optional<Cooling> findByProduct_ProductId(int id);

    @Query("SELECT DISTINCT c FROM Cooling c " +
           "LEFT JOIN FETCH c.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE p.price <= :maxPrice " +
           "AND (p.performanceScore IS NULL OR p.performanceScore >= :minScore) " +
           "AND p.status = true " +
           "AND (p.inventoryQuantity IS NULL OR p.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(p.performanceScore, 50) DESC, p.price ASC")
    List<Cooling> findBestCoolingByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                   @Param("minScore") int minScore);

    @Query("SELECT DISTINCT c FROM Cooling c " +
           "LEFT JOIN FETCH c.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand")
    List<Cooling> findAllWithImages();

    @Query("SELECT DISTINCT c FROM Cooling c " +
           "LEFT JOIN FETCH c.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE c.id = :id")
    Optional<Cooling> findByIdWithImages(@Param("id") int id);
}
