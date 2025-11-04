package com.example.PCOnlineShop.controller.category;

import com.example.PCOnlineShop.service.product.ProductService;
import org.springframework.ui.Model;
import com.example.PCOnlineShop.service.product.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/category")
@PreAuthorize(("hasRole('ADMIN')"))
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/category-list";
    }
    @GetMapping("/{id}/products")
    public String viewProductsByCategory(@PathVariable("id") Integer categoryId, Model model) {
        var category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        model.addAttribute("category", category);
        model.addAttribute("products", productService.getByCategoryId(categoryId));
        return "product/product-by-category"; // trang mới để hiển thị
    }
}
