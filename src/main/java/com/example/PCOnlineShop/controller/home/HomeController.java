package com.example.PCOnlineShop.controller.home;

import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.model.product.Category;
import com.example.PCOnlineShop.service.product.ProductService;
import com.example.PCOnlineShop.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * ✅ Trang chủ hiển thị tất cả sản phẩm, hỗ trợ:
     * - Phân trang
     * - Lọc theo danh mục
     * - Tìm kiếm theo tên sản phẩm
     */
    @GetMapping({"", "home"})
    public String showHomePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "") String search,
            Model model
    ) {
        // 🔹 1. Lấy danh sách category để hiển thị sidebar
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        // 🔹 2. Xác định sản phẩm cần hiển thị
        Page<Product> productPage;

        if (search != null && !search.trim().isEmpty()) {
            // 🔍 Nếu có từ khóa tìm kiếm
            productPage = productService.searchProducts(search.trim(), page, size);
            model.addAttribute("search", search);
        } else if (categoryId != null) {
            // 🧩 Nếu chọn danh mục cụ thể
            productPage = productService.getProductsByCategory(categoryId, page, size);
            model.addAttribute("currentCategory", categoryId);
        } else {
            // 📦 Nếu không có điều kiện gì -> lấy tất cả sản phẩm
            productPage = productService.getAllProduct(page, size);
        }

        // 🔹 3. Truyền dữ liệu ra view
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);

        return "home"; // -> home.html
    }
}
