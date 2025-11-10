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

    public boolean areSimilarBrand(String name1, String name2){
        if (name1 == null || name2 == null){
            return false;
        }
        name1 = name1.toLowerCase().trim();
        name2 = name2.toLowerCase().trim();

        int distance = levenshteinDistance(name1, name2);
        int maxLength = Math.max(name1.length(), name2.length());
        double similarity = 1 - (double) distance / maxLength;
        return similarity >= 0.6;
    }
    // Use Levenshtein Distance to check the similar between to string
    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[a.length()][b.length()];
    }
}
