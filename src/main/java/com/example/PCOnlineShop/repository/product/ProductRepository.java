package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.model.product.Category;
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

    // ManyToMany: Tìm product có chứa category_id trong danh sách categories
    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    Page<Product> findByCategory_CategoryId(@Param("categoryId") int categoryId, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId AND p.productId != :currentProductId ORDER BY RAND()")
    List<Product> findTop4ByCategory_CategoryIdAndProductIdNot(@Param("categoryId") Integer categoryId, @Param("currentProductId") Integer currentProductId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images")
    List<Product> findAllWithImages();

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images WHERE p.productId = :productId")
    Product findByIdWithImages(@Param("productId") Integer productId);

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN p.brand b
        LEFT JOIN p.categories c
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

    List<Product> findTop8ByStatusTrue();

    // Tìm products có category cụ thể và đang active
    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c = :category AND p.status = true")
    List<Product> findByCategoryAndStatusTrue(@Param("category") Category category);

    List<Product> findByBrandAndStatusTrue(Brand brand);

    List<Product> findByStatusTrue();

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN p.categories c
        WHERE p.status = true
          AND (:categoryId IS NULL OR c.categoryId = :categoryId)
          AND (:brandId IS NULL OR p.brand.brandId = :brandId)
        """)
    Page<Product> searchProducts(
            @Param("categoryId") Integer categoryId,
            @Param("brandId") Integer brandId,
            Pageable pageable
    );

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c = :category AND p.status = true ORDER BY RAND()")
    List<Product> findTop8ByCategoryAndStatusTrue(@Param("category") Category category);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    Page<Product> findByCategory_CategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);
    Page<Product> findByBrand_BrandId(Integer brandId, Pageable pageable);

    @Query(value = """
            SELECT DISTINCT p.* FROM product p
            LEFT JOIN product_category pc ON p.product_id = pc.product_id
            WHERE (:categoryId IS NULL OR pc.category_id = :categoryId)
              AND (:brandId IS NULL OR p.brand_id = :brandId)
              AND (:keyword IS NULL OR LOWER(p.product_name) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND p.status = TRUE
            """,
            countQuery = """
            SELECT COUNT(DISTINCT p.product_id) FROM product p
            LEFT JOIN product_category pc ON p.product_id = pc.product_id
            WHERE (:categoryId IS NULL OR pc.category_id = :categoryId)
              AND (:brandId IS NULL OR p.brand_id = :brandId)
              AND (:keyword IS NULL OR LOWER(p.product_name) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
              AND p.status = TRUE
            """,
            nativeQuery = true)
    Page<Product> searchProducts(
            @Param("categoryId") Integer categoryId,
            @Param("brandId") Integer brandId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
