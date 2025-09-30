package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Product;
import org.springframework.stereotype.Service;
import com.example.PCOnlineShop.repository.product.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int createProduct(Product product) {
        Product saveProduct = productRepository.save(product);
        return saveProduct.getProductId();
    }
}
