package com.example.PCOnlineShop.controller.category;

import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.CategoryRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * ✅ Hiển thị tất cả sản phẩm thuộc một category
     */
    @GetMapping("/{id}")
    public String viewCategoryProducts(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "productId") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            Model model
    ) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            model.addAttribute("error", "Category not found!");
            return "error/404";
        }

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findByCategory_CategoryId(id, pageable);

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", category);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("pageNumbers",
                java.util.stream.IntStream.range(0, productPage.getTotalPages()).boxed().toList());

        return "product/category-products";
    }
}
