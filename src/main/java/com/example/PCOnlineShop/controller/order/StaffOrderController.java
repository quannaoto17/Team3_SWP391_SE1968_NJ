package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/orders")
public class StaffOrderController {

    private final OrderService orderService;

    public StaffOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ==============================
    // 🔹 Danh sách đơn hàng (Staff)
    // ==============================
    @GetMapping("/list")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "stafforder/staff-order-list"; // View trong templates/stafforder/
    }

    // ==============================
    // 🔹 Xem chi tiết đơn hàng
    // ==============================
    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) return "redirect:/staff/orders/list";
        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));
        return "stafforder/staff-order-detail";
    }

    // ==============================
    // 🔹 Cập nhật trạng thái đơn hàng
    // ==============================
    @PostMapping("/update-status/{id}")
    public String updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/staff/orders/list";
    }
}