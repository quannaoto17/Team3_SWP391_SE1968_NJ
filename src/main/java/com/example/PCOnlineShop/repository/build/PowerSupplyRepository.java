package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.PowerSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PowerSupplyRepository extends JpaRepository<PowerSupply,Integer> {
    Optional<PowerSupply> findByProduct_ProductId(int id);

    @Query("SELECT p FROM PowerSupply p " +
           "WHERE p.product.price <= :maxPrice " +
           "AND (p.product.performanceScore IS NULL OR p.product.performanceScore >= :minScore) " +
           "AND p.product.status = true " +
           "AND (p.product.inventoryQuantity IS NULL OR p.product.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(p.product.performanceScore, 50) DESC, p.product.price ASC")
    List<PowerSupply> findBestPsuByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                   @Param("minScore") int minScore);
}
