package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Brand;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
