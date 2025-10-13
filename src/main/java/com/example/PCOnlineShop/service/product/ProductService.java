package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.repository.product.ImageRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
                          CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAllWithImages();
    }


    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> getAllProduct(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Product> getProductsByCategory(int categoryId, int page, int size) {
        return productRepository.findByCategory_CategoryId(categoryId, PageRequest.of(page, size));
    }

    // 🧩 Phân trang + Sắp xếp tổng quát
    public Page<Product> getProducts(int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return productRepository.findAll(pageable);
    }

    // 🧩 Lọc theo Brand (kèm phân trang)
    public Page<Product> getProductsByBrand(int brandId, int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return productRepository.findByBrand_BrandId(brandId, pageable);
    }

    // 🧩 Lọc theo Category (kèm phân trang)
    public Page<Product> getProductsByCategory(int categoryId, int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return productRepository.findByCategory_CategoryId(categoryId, pageable);
    }

    // Search di dộng
    public Page<Product> search(String keyword, Integer brandId, Integer categoryId,
                                int page, int size, String sortField, String sortDir) {
        String field = (sortField != null && List.of("productId","productName","price","createAt","status")
                .contains(sortField)) ? sortField : "productId";

        Sort sort = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        String kw = (keyword == null || keyword.isBlank()) ? null : keyword.trim();
        return productRepository.search(kw, brandId, categoryId, pageable);
    }

}
