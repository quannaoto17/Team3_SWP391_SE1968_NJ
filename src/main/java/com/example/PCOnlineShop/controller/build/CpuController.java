package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItem;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CpuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@AllArgsConstructor
@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class CpuController {
    private CpuService cpuService;
    private BuildService buildService;

    // Hiển thị danh sách CPU
    @GetMapping("/cpu")
    public String showCpuPage(@ModelAttribute("buildItems") BuildItem buildItem, Model model) {
        model.addAttribute("build", buildService.getCompatibleCpus(buildItem.getMainboardId()));
        return "/build/cpu/build-cpu";
    }
    // Hiển thị chi tiết CPU
    // Thêm, sửa, xóa CPU sẽ do admin thực hiện qua trang admin
}
