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

    @Query("SELECT DISTINCT s FROM Storage s " +
           "LEFT JOIN FETCH s.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE p.price <= :maxPrice " +
           "AND (p.performanceScore IS NULL OR p.performanceScore >= :minScore) " +
           "AND p.status = true " +
           "AND (p.inventoryQuantity IS NULL OR p.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(p.performanceScore, 50) DESC, p.price ASC")
    List<Storage> findBestStorageByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                   @Param("minScore") int minScore);

    @Query("SELECT DISTINCT s FROM Storage s " +
           "LEFT JOIN FETCH s.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand")
    List<Storage> findAllWithImages();

    @Query("SELECT DISTINCT s FROM Storage s " +
           "LEFT JOIN FETCH s.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE s.id = :id")
    Optional<Storage> findByIdWithImages(@Param("id") int id);
}
