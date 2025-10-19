package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.service.product.ProductService;
import com.example.PCOnlineShop.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) return "redirect:/home";

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("images", product.getImages());

        if (product.getCategory() != null) {
            List<Product> related = productService.getTopRelatedProducts(product.getCategory().getCategoryId(), id);
            model.addAttribute("relatedProducts", related);
        }

        return "product/product-details"; // tương ứng với templates/product/product-details.html
    }
}
