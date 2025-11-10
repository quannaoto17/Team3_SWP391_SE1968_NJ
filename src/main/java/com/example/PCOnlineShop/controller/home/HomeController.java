package com.example.PCOnlineShop.controller.home;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.product.BrandRepository;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import com.example.PCOnlineShop.service.product.CategoryService;
import com.example.PCOnlineShop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    /**
     * ✅ Trang landing (trang chủ đầu tiên)
     */

   @GetMapping("/")
    public String landing() {
        return "landing";
    }

    /**
     * ✅ Trang Home (hiển thị sản phẩm nổi bật, lọc theo brand/category)
     */
    @GetMapping("/home")
    public String home(
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer brand,
            Authentication authentication,
            Model model
    ) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Account user = accountRepository.findByEmail(email).orElse(null);
            model.addAttribute("currentUser", user);
        }

        List<Category> categories = categoryRepository.findAll();
        List<Brand> brands = brandRepository.findAll();
        List<Product> products;

        if (category != null) {
            products = productService.getProductsByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else if (brand != null) {
            products = productService.getProductsByBrand(brand);
            model.addAttribute("selectedBrand", brand);
        } else {
            products = productService.getFeaturedProducts();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("featuredProducts", products);

        return "home";
    }

    /**
     * ✅ Trang Product Home - hiển thị toàn bộ sản phẩm (phân trang + lọc)
     */
    @GetMapping("/products")
    public String productHome(
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "price") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // ✅ Gọi trực tiếp repository
        Page<Product> productPage = productRepository.searchProducts(category, brand, minPrice, maxPrice, keyword, pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("pageNumbers",
                java.util.stream.IntStream.range(0, productPage.getTotalPages()).boxed().toList());

        // Truyền các giá trị filter về view
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortDir", sortDir);

        return "product/product-home";
    }


    /**
     * ✅ Trang chi tiết sản phẩm
     */
    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        List<Product> related = productService.getRelatedProducts(product);

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", related);

        return "product/product-details";
    }
}
