package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Image;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.repository.product.ImageRepository;
import com.example.PCOnlineShop.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/staff/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ImageRepository imageRepository;

    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository,
                             BrandRepository brandRepository,
                             ImageRepository imageRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.imageRepository = imageRepository;
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
        return "product/product-form";
    }

    // ===== LƯU SẢN PHẨM MỚI (CÓ ẢNH) =====
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("category.categoryId") int categoryId,
                              @RequestParam("brand.brandId") int brandId,
                              @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        // 1️⃣ Lưu sản phẩm trước để có productId
        Product savedProduct = productService.addProduct(product);

        // 2️⃣ Thư mục lưu ảnh: /static/image
        Path uploadPath = Paths.get(new File("src/main/resources/static/image").getAbsolutePath());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3️⃣ Lưu từng ảnh
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Image image = new Image();
                image.setImageUrl("/image/" + fileName);
                image.setProduct(savedProduct);
                images.add(image);
            }
        }

        // 4️⃣ Lưu vào DB
        imageRepository.saveAll(images);
        savedProduct.setImages(images);
        productService.updateProduct(savedProduct);

        return "redirect:/staff/products/list";
    }

    // ===== FORM CHỈNH SỬA =====
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/staff/products/list";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-update";
    }

    // ===== CẬP NHẬT SẢN PHẨM (CÓ ẢNH MỚI) =====
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("category.categoryId") int categoryId,
                                @RequestParam("brand.brandId") int brandId,
                                @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws IOException {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        // Lưu lại product
        Product updatedProduct = productService.updateProduct(product);

        // Nếu có ảnh mới thì thêm vào static/image
        if (imageFiles != null && !imageFiles.isEmpty()) {
            Path uploadPath = Paths.get("src/main/resources/static/image");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            List<Image> newImages = new ArrayList<>();
            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    Image image = new Image();
                    image.setImageUrl("/image/" + fileName);
                    image.setProduct(updatedProduct);
                    newImages.add(image);
                }
            }
            imageRepository.saveAll(newImages);
        }

        return "redirect:/staff/products/list";
    }
}
