package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
