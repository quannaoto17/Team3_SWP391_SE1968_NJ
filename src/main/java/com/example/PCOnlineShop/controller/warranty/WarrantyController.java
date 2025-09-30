package com.example.PCOnlineShop.controller.warranty;

import com.example.PCOnlineShop.model.warranty.CustomerWarrantyResult;
import com.example.PCOnlineShop.service.warranty.WarrantyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WarrantyController {

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    // Màn form tìm kiếm
    @GetMapping("/warranty")
    public String form() {
        return "Staff/check-warranty";
    }

    // Tìm theo số điện thoại, optional: chọn orderId để xem chi tiết
    @GetMapping("/warranty/search")
    public String search(@RequestParam("phone") String phone,
                         @RequestParam(value = "orderId", required = false) Integer orderId,
                         Model model) {

        CustomerWarrantyResult result = warrantyService.searchByPhone(phone);

        model.addAttribute("queryPhone", phone);
        model.addAttribute("result", result);

        // nếu không truyền orderId: mặc định lấy order đầu tiên
        Integer selected = orderId;
        if (selected == null && !result.getOrders().isEmpty()) {
            selected = result.getOrders().get(0).getOrderId();
        }
        model.addAttribute("selectedOrderId", selected);
        model.addAttribute("itemsOfSelectedOrder",
                selected == null ? null : result.getItemsByOrderId().get(selected));

        return "Staff/check-warranty";
    }
}
