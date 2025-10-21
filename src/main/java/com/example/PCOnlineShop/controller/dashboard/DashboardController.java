package com.example.PCOnlineShop.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard/admin")
    public String adminDashboard(Model model) {
        // Dữ liệu mẫu (bạn có thể lấy từ service thật)
        model.addAttribute("totalUsers", 1243);
        model.addAttribute("totalOrders", 382);
        model.addAttribute("revenue", 125430000);
        model.addAttribute("totalProducts", 89);

        return "dashboard-admin";
    }
    @GetMapping("/dashboard/staff")
    public String staffDashboard(Model model) {
        model.addAttribute("pendingOrders", 15);
        model.addAttribute("shippedOrders", 48);
        model.addAttribute("productsInStock", 220);
        model.addAttribute("feedbackCount", 34);

        return "dashboard-staff";
    }
}
