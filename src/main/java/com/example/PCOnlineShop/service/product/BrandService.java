package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public BrandService(BrandRepository brandRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    // Lấy tất cả brand
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    // Lấy brand theo id
    public Brand getBrandById(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    // Thêm brand mới
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    //Kiểm tra trùng tên brand
    public boolean existsBrandByName(String name) {
        return brandRepository.existsBrandByNameIgnoreCase(name);
    }
    public long countProductsOfBrand(Integer brandId){
        return productRepository.countByBrand_BrandId(brandId);
    }


    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }
    public void reassignProducts(Integer sourceId, Integer targetId) {
        productRepository.reassignBrandByIds(sourceId, targetId);
    }
}
