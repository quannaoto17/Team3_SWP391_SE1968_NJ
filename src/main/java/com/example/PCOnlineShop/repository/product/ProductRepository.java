package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByBrand_BrandId(int brandId, Pageable pageable);

    Page<Product> findByCategory_CategoryId(int categoryId, Pageable pageable);

    List<Product> findTop4ByCategory_CategoryIdAndProductIdNot(Integer categoryId, Integer currentProductId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images")
    List<Product> findAllWithImages();

    @Query("""
        SELECT p FROM Product p
        LEFT JOIN p.brand b
        LEFT JOIN p.category c
        WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:brandId IS NULL OR b.brandId = :brandId)
          AND (:categoryId IS NULL OR c.categoryId = :categoryId)
    """)
    Page<Product> search(@Param("keyword") String keyword,
                         @Param("brandId") Integer brandId,
                         @Param("categoryId") Integer categoryId,
                         Pageable pageable);
    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByProductNameAndStatusTrue(String productName);

    boolean existsByProductName(String productName);

    Optional<Product> findWithDetailsByProductId(int productId);

    public long countByBrand_BrandId(Integer brandId);

    @Query("UPDATE Product p SET p.brand.brandId = :targetId WHERE p.brand.brandId = :sourceId")
    void reassignBrandByIds(@Param("sourceId") Integer sourceId,
                            @Param("targetId") Integer targetId);

}
