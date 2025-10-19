package com.example.PCOnlineShop.controller.staff; // Use appropriate package

import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff/shipping")
@RequiredArgsConstructor
public class StaffShippingController {

    private final OrderService orderService;

    // GET /staff/shipping/list -> Display the shipping management list (Updated)
    @GetMapping("/list")
    public String viewShippingList(Model model) {
        // Call the updated service method
        List<Order> shippingQueueOrders = orderService.getShippingQueueOrders();
        model.addAttribute("shippingOrders", shippingQueueOrders);
        return "staffshipping/shipping-list";
    }

    // POST /staff/shipping/update-status/{orderId} -> Handle status updates (Keep as is)
    @PostMapping("/update-status/{orderId}")
    public String updateShippingOrderStatus(@PathVariable int orderId,
                                            @RequestParam String newStatus,
                                            RedirectAttributes redirectAttributes) {
        try {
            // 1. Lấy trạng thái hiện tại của đơn hàng TRƯỚC KHI cập nhật
            Order currentOrder = orderService.getOrderById(orderId); // Lấy thông tin đơn hàng

            if (currentOrder == null) {
                redirectAttributes.addFlashAttribute("error", "Order #" + orderId + " not found.");
                return "redirect:/staff/shipping/list";
            }

            // 2. So sánh trạng thái mới và cũ
            if (currentOrder.getStatus().equals(newStatus)) {
                // Nếu trạng thái không đổi -> Báo cho người dùng biết
                redirectAttributes.addFlashAttribute("info", "No status change detected for Order #" + orderId + ".");
            } else {
                // Nếu trạng thái thay đổi -> Gọi service để cập nhật
                orderService.updateShippingStatus(orderId, newStatus);
                redirectAttributes.addFlashAttribute("success", "Order #" + orderId + " status updated to " + newStatus);
            }
        } catch (Exception e) {
            System.err.println("Error updating shipping status: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/staff/shipping/list"; // Quay lại danh sách
    }
}