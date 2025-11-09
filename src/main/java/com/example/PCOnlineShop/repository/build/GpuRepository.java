package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.GPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GpuRepository extends JpaRepository<GPU, Integer> {
    Optional<GPU> findByProduct_ProductId(int id);

    @Query("SELECT g FROM GPU g " +
           "WHERE g.product.price <= :maxPrice " +
           "AND (g.product.performanceScore IS NULL OR g.product.performanceScore >= :minScore) " +
           "AND g.product.status = true " +
           "AND (g.product.inventoryQuantity IS NULL OR g.product.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(g.product.performanceScore, 50) DESC, g.product.price ASC")
    List<GPU> findBestGpusByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                            @Param("minScore") int minScore);
}
