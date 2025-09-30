package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.repository.product.BrandRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {
    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    PostMapping("/brand")
    private long
}
