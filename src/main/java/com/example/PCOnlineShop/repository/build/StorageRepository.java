package com.example.PCOnlineShop.repository.build;

import com.example.PCOnlineShop.model.build.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage,Integer> {
    Optional<Storage> findByProduct_ProductId(int id);

    @Query("SELECT s FROM Storage s " +
           "WHERE s.product.price <= :maxPrice " +
           "AND (s.product.performanceScore IS NULL OR s.product.performanceScore >= :minScore) " +
           "AND s.product.status = true " +
           "AND (s.product.inventoryQuantity IS NULL OR s.product.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(s.product.performanceScore, 50) DESC, s.product.price ASC")
    List<Storage> findBestStorageByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                   @Param("minScore") int minScore);
}
