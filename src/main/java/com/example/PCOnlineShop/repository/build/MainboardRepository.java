package com.example.PCOnlineShop.repository.build;
import com.example.PCOnlineShop.model.build.Mainboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MainboardRepository extends JpaRepository<Mainboard, Integer> {
    Optional<Mainboard> findByProduct_ProductId(int id);

    @Query("SELECT m FROM Mainboard m " +
           "JOIN FETCH m.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE p.price <= :maxPrice " +
           "AND (p.performanceScore IS NULL OR p.performanceScore >= :minScore) " +
           "AND p.status = true " +
           "AND (p.inventoryQuantity IS NULL OR p.inventoryQuantity > 0) " +
           "ORDER BY COALESCE(p.performanceScore, 50) DESC, p.price ASC")
    List<Mainboard> findBestMainboardsByBudgetAndScore(@Param("maxPrice") double maxPrice,
                                                        @Param("minScore") int minScore);

    @Query("SELECT m FROM Mainboard m " +
           "JOIN FETCH m.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand")
    List<Mainboard> findAllWithImages();

    @Query("SELECT m FROM Mainboard m " +
           "JOIN FETCH m.product p " +
           "LEFT JOIN FETCH p.images " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE m.productId = :id")
    Optional<Mainboard> findByIdWithImages(@Param("id") int id);
}
