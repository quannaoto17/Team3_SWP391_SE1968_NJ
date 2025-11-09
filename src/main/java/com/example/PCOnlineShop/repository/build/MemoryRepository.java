package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Integer> {
    Optional<Memory> findByProduct_ProductId(int id);

    @Query("SELECT m FROM Memory m " +
           "WHERE m.product.price <= :maxPrice " +
           "AND (m.product.performanceScore IS NULL OR m.product.performanceScore >= :minScore) " +
           "AND m.product.status = true " +
           "AND (m.product.inventoryQuantity IS NULL OR m.product.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(m.product.performanceScore, 50) DESC, m.product.price ASC")
    List<Memory> findBestMemoryByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                 @Param("minScore") int minScore);
}
