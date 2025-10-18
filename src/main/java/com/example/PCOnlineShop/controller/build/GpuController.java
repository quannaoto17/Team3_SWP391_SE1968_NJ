package com.example.PCOnlineShop.controller.build;


import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.GPU;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.GpuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
@AllArgsConstructor
public class GpuController {
    private final BuildService buildService;
    private final GpuService gpuService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    // Hien thi danh sach GPU
    @GetMapping("/gpu")
    public String showGpuPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("gpus", buildService.getCompatibleGPUs(buildItem));
        model.addAttribute("allBrands", gpuService.getAllBrands());
        return "/build/build-gpu";
    }

    // Filter GPU by brands
    @PostMapping("/gpu/filter")
    public String filterGpus(@RequestParam(required = false) List<String> brands,
                             @RequestParam(required = false) String sortBy,
                             @ModelAttribute("buildItems") BuildItemDto buildItem,
                             Model model) {
        List<GPU> gpus = buildService.getCompatibleGPUs(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brand", brands);
        gpus = gpuService.filterGpus(gpus, filters, sortBy);

        model.addAttribute("gpus", gpus);
        model.addAttribute("allBrands", gpuService.getAllBrands());
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
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
