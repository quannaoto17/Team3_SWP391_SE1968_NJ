package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer/orders")
public class CustomerOrderController {

    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================================
    // 🔹 Danh sách đơn hàng của Customer
    // ================================
    @GetMapping("/list")
    public String viewMyOrders(
            @SessionAttribute(value = "loggedInAccount", required = false) Account account,
            Model model) {

        // Nếu chưa có session đăng nhập -> fake 1 tài khoản để test (id=1)
        if (account == null) {
            account = new Account();
            account.setAccountId(1); // ✅ ID customer tồn tại trong DB
        }

        // Lấy danh sách đơn hàng theo account
        model.addAttribute("orders", orderService.getOrdersByAccount(account));

        // Trả về view tương ứng
        return "customerorder/my-order-list"; // ✅ View nằm trong templates/customerorder/
    }

    // ================================
    // 🔹 Chi tiết đơn hàng
    // ================================
    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            // Nếu không tìm thấy đơn -> quay lại danh sách
            return "redirect:/customer/orders/list";
        }

        // Gửi dữ liệu sang view
        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));

        return "customerorder/my-order-detail"; // ✅ View nằm trong templates/customerorder/
    }
}
