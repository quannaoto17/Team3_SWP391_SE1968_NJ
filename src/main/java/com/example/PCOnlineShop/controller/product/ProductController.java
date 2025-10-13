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

    // ===== DANH SÁCH SẢN PHẨM (có keyword để search, giữ phân trang + sort) =====
    @GetMapping("/list")
    public String listProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "productId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,   // NEW
            Model model) {

        Page<Product> productPage;

        boolean hasKeyword = (keyword != null && !keyword.isBlank());
        if (hasKeyword) {
            productPage = productService.search(keyword, brandId, categoryId, page, size, sortField, sortDir);
        } else if (brandId != null) {
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
        model.addAttribute("keyword", keyword); // NEW

        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/product-list";
    }

    // ===== LIVE SEARCH: Trả về fragment <tbody> đã render để JS thay trực tiếp =====
    // View cần khai báo: <tbody id="productTbody" th:fragment="tbodyRows">...</tbody>
    @GetMapping("/search-fragment")
    public String searchFragment(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Integer brandId,
                                 @RequestParam(required = false) Integer categoryId,
                                 @RequestParam(defaultValue = "productId") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDir,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "8") int size,
                                 Model model) {

        Page<Product> productPage =
                productService.search(keyword, brandId, categoryId, page, size, sortField, sortDir);

        model.addAttribute("products", productPage.getContent());
        // Nếu muốn update cả pagination theo kết quả search, có thể add currentPage/totalPages và trả fragment lớn hơn
        return "product/product-list :: tbodyRows";
    }
    // Trả về cả bảng + pagination theo keyword/filter/sort/page hiện thời
    @GetMapping("/search-block")
    public String searchBlock(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) Integer brandId,
                              @RequestParam(required = false) Integer categoryId,
                              @RequestParam(defaultValue = "productId") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "8") int size,
                              Model model) {

        Page<Product> productPage = productService.search(keyword, brandId, categoryId, page, size, sortField, sortDir);

        model.addAttribute("products",     productPage.getContent());
        model.addAttribute("currentPage",  page);
        model.addAttribute("totalPages",   productPage.getTotalPages());
        model.addAttribute("totalItems",   productPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir",   sortDir);
        model.addAttribute("reverseSortDir", "asc".equalsIgnoreCase(sortDir) ? "desc" : "asc");

        model.addAttribute("brandId",  brandId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword",  keyword);



        return "product/product-list :: listWrapper"; // fragment mới (xem bước 2)
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
                              @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles) throws IOException {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        // 1) Lưu sản phẩm trước để có productId
        Product savedProduct = productService.addProduct(product);

        // 2) Lưu ảnh (nếu có)
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> images = saveImagesToStatic(imageFiles, savedProduct);
            // 3) Lưu vào DB
            imageRepository.saveAll(images);
            savedProduct.setImages(images);
            productService.updateProduct(savedProduct);
        }

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

    // ===== CẬP NHẬT SẢN PHẨM (CÓ THỂ THÊM ẢNH MỚI) =====
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
            List<Image> newImages = saveImagesToStatic(imageFiles, updatedProduct);
            imageRepository.saveAll(newImages);
        }

        return "redirect:/staff/products/list";
    }

    // ===== Helper: Lưu file ảnh vào /static/image và tạo entity Image =====
    private List<Image> saveImagesToStatic(List<MultipartFile> imageFiles, Product product) throws IOException {
        // Thư mục lưu ảnh: /src/main/resources/static/image
        Path uploadPath = Paths.get(new File("src/main/resources/static/image").getAbsolutePath());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Image img = new Image();
                img.setImageUrl("/image/" + fileName); // URL để Thymeleaf hiển thị
                img.setProduct(product);
                images.add(img);
            }
        }
        return images;
    }
}
