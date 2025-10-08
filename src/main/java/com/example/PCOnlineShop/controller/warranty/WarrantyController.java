package com.example.PCOnlineShop.controller.warranty;

import org.springframework.ui.Model;
import com.example.PCOnlineShop.service.warranty.WarrantyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/staff/warranty")
public class WarrantyController {

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @GetMapping("/check")
    public String checkPage() {
        return "warranty/check-warranty";
    }

    @PostMapping("/search")
    public String searchByPhone(@RequestParam String phone,
                                @RequestParam(required = false) Integer orderId,
                                Model model) {
        model.addAttribute("orders", warrantyService.getOrdersByPhone(phone));
        model.addAttribute("phone", phone);

        if (orderId != null) {
            model.addAttribute("products", warrantyService.getProductsInOrder(orderId));
            model.addAttribute("selectedOrderId", orderId);
        }

        return "warranty/check-warranty";
    }
}
