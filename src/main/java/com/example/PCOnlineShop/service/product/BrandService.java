package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.repository.product.BrandRepository;

public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
}
