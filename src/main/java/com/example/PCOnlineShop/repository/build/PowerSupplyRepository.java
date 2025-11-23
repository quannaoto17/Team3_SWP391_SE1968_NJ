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

    @Query("SELECT DISTINCT p FROM PowerSupply p " +
           "LEFT JOIN FETCH p.product pr " +
           "LEFT JOIN FETCH pr.images " +
           "LEFT JOIN FETCH pr.brand " +
           "WHERE pr.price <= :maxPrice " +
           "AND (pr.performanceScore IS NULL OR pr.performanceScore >= :minScore) " +
           "AND pr.status = true " +
           "AND (pr.inventoryQuantity IS NULL OR pr.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(pr.performanceScore, 50) DESC, pr.price ASC")
    List<PowerSupply> findBestPsuByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                   @Param("minScore") int minScore);

    @Query("SELECT DISTINCT p FROM PowerSupply p " +
           "LEFT JOIN FETCH p.product pr " +
           "LEFT JOIN FETCH pr.images " +
           "LEFT JOIN FETCH pr.brand")
    List<PowerSupply> findAllWithImages();

    @Query("SELECT DISTINCT p FROM PowerSupply p " +
           "LEFT JOIN FETCH p.product pr " +
           "LEFT JOIN FETCH pr.images " +
           "LEFT JOIN FETCH pr.brand " +
           "WHERE p.id = :id")
    Optional<PowerSupply> findByIdWithImages(@Param("id") int id);
}
