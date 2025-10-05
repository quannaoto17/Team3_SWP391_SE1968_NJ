package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByBrand_BrandId(int brandId, Pageable pageable);

    Page<Product> findByCategory_CategoryId(int categoryId, Pageable pageable);
}
