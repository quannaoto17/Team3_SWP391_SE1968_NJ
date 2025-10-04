package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/products")
public class ProductController {

    private final ProductService productService;


    private final CategoryRepository categoryRepository;


    private final  BrandRepository brandRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    // ===== LIST =====
    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "product/product-list";  // thymeleaf file: templates/product/product-list.html
    }

    // ===== DETAIL =====
    @GetMapping("/{id}")
    public String detailProduct(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/product-detail"; // thymeleaf file: templates/product/product-detail.html
    }

    // ===== ADD FORM =====
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-form";   // form dùng cho cả add và edit
    }

    // ===== SAVE PRODUCT =====
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/staff/products/list";
    }

    // ===== EDIT FORM =====
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-form";   // dùng chung form
    }

    // ===== UPDATE PRODUCT =====
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product);
        return "redirect:/staff/products/list";
    }


}
