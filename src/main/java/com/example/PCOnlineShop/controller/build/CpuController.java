package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemId;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CpuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class CpuController {
    private CpuService cpuService;
    private BuildService buildService;

    // Hiển thị danh sách CPU
    @GetMapping("/cpu")
    public String showCpuPage(@ModelAttribute("buildItems") BuildItemId buildItemId, Model model) {
        model.addAttribute("cpus", buildService.getCompatibleCpus(buildItemId.getMainboardId()));
        return "/build/build-cpu";
    }

    // Chọn CPU
    // Chọn CPU sẽ lưu vào buildItem và chuyển sang bước chọn linh kiện tiếp theo
    @PostMapping("/selectCpu")
    public String selectCpu(@RequestParam int cpuId,
                            @ModelAttribute("buildItems") BuildItemId buildItemId) {
        buildItemId.setCpuId(cpuId);
        return "redirect:/build/gpu";
    }
    // Hiển thị chi tiết CPU
    // Thêm, sửa, xóa CPU sẽ do admin thực hiện qua trang admin
}
