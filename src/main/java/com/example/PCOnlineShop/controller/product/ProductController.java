package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository,
                             BrandRepository brandRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    // ===== DANH SÁCH SẢN PHẨM =====
    @GetMapping("/list")
    public String listProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "productId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            Model model) {

        Page<Product> productPage;

        if (brandId != null) {
            productPage = productService.getProductsByBrand(brandId, page, size, sortField, sortDir);
        } else if (categoryId != null) {
            productPage = productService.getProductsByCategory(categoryId, page, size, sortField, sortDir);
        } else {
            productPage = productService.getProducts(page, size, sortField, sortDir);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("brandId", brandId);
        model.addAttribute("categoryId", categoryId);

        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());

        return "product/product-list";
    }

    // ===== FORM THÊM SẢN PHẨM =====
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-form"; // ✅ Đổi tên file cho đúng mục đích
    }

    // ===== LƯU SẢN PHẨM MỚI =====
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("category.categoryId") int categoryId,
                              @RequestParam("brand.brandId") int brandId) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        productService.addProduct(product);
        return "redirect:/staff/products/list";
    }

    // ====== CHỈNH SỬA SẢN PHẨM ======
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/staff/products/list";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product-update";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("category.categoryId") int categoryId,
                                @RequestParam("brand.brandId") int brandId) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        productService.updateProduct(product);
        return "redirect:/staff/products/list";
    }
}
