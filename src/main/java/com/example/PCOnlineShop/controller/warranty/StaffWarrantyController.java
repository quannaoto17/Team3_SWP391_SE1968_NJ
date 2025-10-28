package com.example.PCOnlineShop.controller.warranty; // Đảm bảo đúng package

import com.example.PCOnlineShop.dto.warranty.WarrantyDetailDTO; // Import DTO đúng
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService; // Sử dụng OrderService
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/staff/warranty")
public class StaffWarrantyController {

    private final OrderService orderService;

    public StaffWarrantyController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/check")
    public String showCheckPage(Model model, @RequestParam(required = false) String phone) {
        if (StringUtils.hasText(phone)) {
            model.addAttribute("phone", phone);
        }
        return "warranty/check-warranty";
    }

    @PostMapping("/search")
    public String searchWarrantyByPhone(
            @RequestParam String phone,
            @RequestParam(required = false) Integer orderId,
            Model model) {

        List<Order> orders = orderService.getOrdersByPhoneNumberForWarranty(phone);
        model.addAttribute("orders", orders);
        model.addAttribute("phone", phone);

        if (orderId != null) {
            // Gọi phương thức trả về List<WarrantyDetailDTO>
            List<WarrantyDetailDTO> warrantyDetails = orderService.getWarrantyDetailsByOrderId(orderId);
            model.addAttribute("warrantyDetails", warrantyDetails);
            model.addAttribute("selectedOrderId", orderId);
        }


        return "warranty/check-warranty";
    }
}