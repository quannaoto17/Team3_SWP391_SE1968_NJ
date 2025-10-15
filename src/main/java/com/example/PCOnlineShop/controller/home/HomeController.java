package com.example.PCOnlineShop.controller.home;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.service.product.ProductService;
import com.example.PCOnlineShop.service.product.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/home")
    public String showHomePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) Integer categoryId,
            Model model) {

        // Lấy tất cả category từ DB
        List<Category> categories = categoryService.getAllCategories();

        // Lấy sản phẩm theo category (nếu có)
        Page<Product> products = (categoryId == null)
                ? productService.getAllProduct(page, size)
                : productService.getProductsByCategory(categoryId, page, size);

        model.addAttribute("categories", categories);
        model.addAttribute("productPage", products);
        model.addAttribute("currentCategory", categoryId);

        return "home";
    }
}
