package com.example.PCOnlineShop.controller.dashboard;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackRepository; // nếu có
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final FeedbackRepository feedbackRepository;

    public DashboardController(AccountRepository accountRepository,
                               ProductRepository productRepository,
                               OrderRepository orderRepository,
                               FeedbackRepository feedbackRepository) {
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.feedbackRepository = feedbackRepository;
    }

    // Dashboard Admin
    @GetMapping("/dashboard/admin")
    public String adminDashboard(Model model) {

        long totalUsers = accountRepository.count();
        long totalStaff = accountRepository.countByRole(RoleName.Staff);
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();

        double revenue = orderRepository.findAll()
                .stream()
                .mapToDouble(order -> order.getTotalAmount())
                .sum();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalStaff", totalStaff);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("revenue", revenue);

        return "dashboard-admin";
    }

    // Dashboard Staff
    @GetMapping("/dashboard/staff")
    public String staffDashboard(Model model) {

        // Đơn hàng chờ xử lý
        long pendingOrders = orderRepository.countByStatus("PENDING");

        // Đơn hàng đã giao
        long shippedOrders = orderRepository.countByStatus("SHIPPED");

        // Sản phẩm trong kho
        long productsInStock = productRepository.count(); // nếu muốn lấy số lượng chính xác, có thể tạo method sumQuantityInStock()

        // Feedback / Review
        long feedbackCount = feedbackRepository.count(); // tổng feedback hiện có

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("shippedOrders", shippedOrders);
        model.addAttribute("productsInStock", productsInStock);
        model.addAttribute("feedbackCount", feedbackCount);

        return "dashboard-staff";
    }
}
