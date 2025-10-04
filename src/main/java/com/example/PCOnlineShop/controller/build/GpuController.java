package com.example.PCOnlineShop.controller.build;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
@AllArgsConstructor
public class GpuController {
    // Hien thi danh sach GPU
    @GetMapping("/gpu")
    public String showGpuPage() {

        return "/build/build-gpu";
    }
}
