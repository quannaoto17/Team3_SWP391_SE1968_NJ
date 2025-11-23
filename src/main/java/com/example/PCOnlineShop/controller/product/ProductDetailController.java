package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.service.feedback.FeedbackService;
import com.example.PCOnlineShop.service.product.CategoryService;
import com.example.PCOnlineShop.service.product.ProductService;
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
    private final FeedbackService feedbackService;

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        //  Lấy sản phẩm
        Product product = productService.getProductById(id);
        if (product == null) return "redirect:/home";

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("images", product.getImages());

        //  Lấy sản phẩm liên quan
        List<Category> productCategories = product.getCategories();
        if (productCategories != null && !productCategories.isEmpty()) {
            Category primaryCategory = productCategories.getFirst();
            List<Product> related = productService.getTopRelatedProducts(
                    primaryCategory.getCategoryId(), id);
            model.addAttribute("relatedProducts", related);
        }

        //  Lấy toàn bộ feedback đã được duyệt (Allow) — không phân trang
        var feedbackPage = feedbackService.getAllowedByProduct(id, 0, Integer.MAX_VALUE);
        model.addAttribute("feedbackPage", feedbackPage);


        //  lấy số sao trung bình

        Double avgRating = feedbackService.getAverageRating(id);
        model.addAttribute("avgRating", avgRating);

        long feedbackCount = feedbackPage.getTotalElements();
        model.addAttribute("feedbackCount", feedbackCount);

        return "product/product-details";
    }


}
