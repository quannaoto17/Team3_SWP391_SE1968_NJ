package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
