package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.repository.product.ImageRepository;
import org.springframework.stereotype.Service;
import com.example.PCOnlineShop.repository.product.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private  ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private ImageRepository imageRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(int id){
        return productRepository.findById(id).get();
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

}
