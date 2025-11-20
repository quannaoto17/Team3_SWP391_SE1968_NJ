package com.example.PCOnlineShop.controller.staff;

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

    @GetMapping("/list")
    public String viewShippingList(Model model) {
        List<Order> shippingQueueOrders = orderService.getShippingQueueOrders();
        model.addAttribute("shippingOrders", shippingQueueOrders);
        return "staffshipping/shipping-list";
    }

    @PostMapping("/update-status/{orderId}")
    public String updateShippingOrderStatus(@PathVariable int orderId,
                                            @RequestParam String newStatus,
                                            RedirectAttributes redirectAttributes) {
        try {
            // Service trả về message kết quả (Success, No Change, hoặc Error)
            String result = orderService.processShippingStatusUpdate(orderId, newStatus);

            if (result.startsWith("Success")) {
                redirectAttributes.addFlashAttribute("success", result);
            } else if (result.startsWith("No change")) {
                redirectAttributes.addFlashAttribute("info", result);
            } else {
                redirectAttributes.addFlashAttribute("error", result);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/staff/shipping/list";
    }
}