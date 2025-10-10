package com.example.PCOnlineShop.controller.build;


import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.GpuService;
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
    private final GpuService gpuService;

    // Hien thi danh sach GPU
    @GetMapping("/gpu")
    public String showGpuPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("gpus", buildService.getCompatibleGPUs(buildItem));
        return "/build/build-gpu";
    }

    // Chon GPU
    @PostMapping("/selectGpu")
    public String selectGpu(@RequestParam int gpuId,
                            @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setGpu(gpuService.getGpuById(gpuId));
        return "redirect:/build/case";
    }
}
