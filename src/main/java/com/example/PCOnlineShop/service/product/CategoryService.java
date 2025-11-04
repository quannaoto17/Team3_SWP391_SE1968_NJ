package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(Category category) {

        categoryRepository.save(category);
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

}
