package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.repository.product.ImageRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

   public List<Product> getByCategoryId(Integer categoryId){
        return productRepository.findByCategory_CategoryId(categoryId);
   }

    public Page<Product> getProductsByCategory(Integer categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategory_CategoryId(categoryId, pageable);
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
    public boolean existsByProductName(String productName) {
        return productRepository.existsByProductName(productName);
    }

}
