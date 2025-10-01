package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.repository.build.CpuRepository;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CpuService;
import com.example.PCOnlineShop.service.build.MotherboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/build/cpu")
public class CpuController {
    private CpuService cpuService;
    private BuildService buildService;

    // Hiển thị danh sách CPU
//    @GetMapping
//    public String showCpuPage(org.springframework.ui.Model model) {
//        model.addAttribute("build", buildService.getCompatibleCpus());
//        return "/build/cpu/build-cpu";
//    }
    // Hiển thị chi tiết CPU
    // Thêm, sửa, xóa CPU sẽ do admin thực hiện qua trang admin
}
