package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.CPU;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CpuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class CpuController {
    private CpuService cpuService;
    private BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    // Hiển thị danh sách CPU
    @GetMapping("/cpu")
    public String showCpuPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        List<CPU> cpus = buildService.getCompatibleCpus(buildItem);
        model.addAttribute("cpus", cpus);
        model.addAttribute("allBrands", cpuService.getAllBrands(cpus));
        return "/build/build-cpu";
    }

    // Filter CPU by brands
    @PostMapping("/cpu/filter")
    public String filterCpus(@RequestParam(required = false) List<String> brands,
                             @RequestParam(required = false) String sortBy,
                             @ModelAttribute("buildItems") BuildItemDto buildItem,
                             Model model) {
        List<CPU> cpus = buildService.getCompatibleCpus(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        cpus = cpuService.filterCpus(cpus, filters, sortBy);

        model.addAttribute("cpus", cpus);
        model.addAttribute("allBrands", cpuService.getAllBrands(buildService.getCompatibleCpus(buildItem)));
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "/build/build-cpu";
    }

    // Chọn CPU
    // Chọn CPU sẽ lưu vào buildItem và chuyển sang bước chọn linh kiện tiếp theo
    @PostMapping("/selectCpu")
    public String selectCpu(@RequestParam int cpuId,
                            @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setCpu(cpuService.getCpuById(cpuId));
        return "redirect:/build/gpu";
    }
}
