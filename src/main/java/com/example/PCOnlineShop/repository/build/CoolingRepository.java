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

    @Query("SELECT c FROM Cooling c " +
           "WHERE c.product.price <= :maxPrice " +
           "AND (c.product.performanceScore IS NULL OR c.product.performanceScore >= :minScore) " +
           "AND c.product.status = true " +
           "AND (c.product.inventoryQuantity IS NULL OR c.product.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(c.product.performanceScore, 50) DESC, c.product.price ASC")
    List<Cooling> findBestCoolingByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                   @Param("minScore") int minScore);
}
