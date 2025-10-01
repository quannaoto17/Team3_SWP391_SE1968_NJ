package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.model.build.Motherboard;
import com.example.PCOnlineShop.service.build.MotherboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/build")
public class MotherboardController {
    private final MotherboardService motherboardService;

    // Hiển thị danh sách motherboard
    @GetMapping("/motherboard")
    public String showMotherboardPage(Model model) {
        model.addAttribute("motherboards", motherboardService.getAllMotherboards());
        return "build/motherboards";
    }

    // Hiển thị chi tiết motherboard
    @GetMapping("/{id}")
    public String showMotherboardDetail(@PathVariable int id, Model model) {
        Motherboard motherboard = motherboardService.getMotherboardById(id);
        model.addAttribute("motherboard", motherboard);
        return "/build/motherboard/motherboard-detail";
    }

    // Thêm, sửa, xóa motherboard sẽ do admin thực hiện qua trang admin
}
