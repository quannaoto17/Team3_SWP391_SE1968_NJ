package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
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
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
                          CategoryRepository categoryRepository, ImageRepository imageRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    public List<Product> getFeaturedProducts() {
        return productRepository.findTop8ByStatusTrue();
    }
    public List<Product> getProductsByCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.map(productRepository::findByCategoryAndStatusTrue).orElse(List.of());
    }

    public List<Product> getProductsByBrand(Integer brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        return brand.map(productRepository::findByBrandAndStatusTrue).orElse(List.of());
    }

    public List<Product> getAllActiveProducts() {
        return productRepository.findByStatusTrue();
    }

    public Page<Product> searchProducts(String keyword, int page, int size) {
        // Nếu người dùng nhập khoảng trắng hoặc để trống thì trả về tất cả sản phẩm
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll(PageRequest.of(page, size));
        }

        // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
        return productRepository.findByProductNameContainingIgnoreCase(keyword.trim(), PageRequest.of(page, size));
    }
    // Lấy sản phẩm liên quan cùng category (trừ chính nó)
    public List<Product> getTopRelatedProducts(Integer categoryId, Integer currentProductId) {
        return productRepository.findTop4ByCategory_CategoryIdAndProductIdNot(categoryId, currentProductId);
    }
    public boolean existsActiveProductName(String productName) {
        return productRepository.existsByProductName(productName);
    }

//    public boolean isProductInActiveOrders(int productId) {
//        List<String> activeStatuses = List.of("Pending", "Processing", "Delivering","Ready to Ship");
//        return orderDetailRepository.existsByProduct_ProductIdAndOrder_StatusIn(productId, activeStatuses);
//    }
    public Page<Product> search(Integer categoryId, Integer brandId, Pageable pageable) {
        return productRepository.searchProducts(categoryId, brandId, pageable);
    }

    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> getRelatedProducts(Product product) {
        if (product.getCategory() == null) return List.of();
        return productRepository.findTop8ByCategoryAndStatusTrue(product.getCategory());
    }


}
