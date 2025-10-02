package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
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

    // Cập nhật brand
    public Brand updateBrand(Brand brand) {
        if (brandRepository.existsById(brand.getBrandId())) {
            return brandRepository.save(brand);
        }
        return null;
    }

    // Xoá brand
    public void deleteBrand(int id) {
        brandRepository.deleteById(id);
    }
}
