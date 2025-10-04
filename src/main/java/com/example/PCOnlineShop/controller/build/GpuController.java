package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemId;
import com.example.PCOnlineShop.service.build.BuildService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
@AllArgsConstructor
public class GpuController {
    private final BuildService buildService;

    // Hien thi danh sach GPU
    @GetMapping("/gpu")
    public String showGpuPage(@ModelAttribute("buildItems") BuildItemId buildItemId, Model model) {
        model.addAttribute("gpus", buildService.getCompatibleGPUs(buildItemId));
        return "/build/build-gpu";
    }

    // Chon GPU
    @PostMapping("/selectGpu")
    public String selectGpu(@RequestParam int gpuId,
                            @ModelAttribute("buildItems") BuildItemId buildItemId) {
        buildItemId.setGpuId(gpuId);
        return "redirect:/build/case";
    }
}
