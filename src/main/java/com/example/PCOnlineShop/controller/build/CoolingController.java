package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Cooling;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CoolingService;
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
public class CoolingController {
    private final CoolingService coolingService;
    private final BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/cooling")
    public String showCoolingPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("coolings", buildService.getCompatibleCoolings(buildItem));
        model.addAttribute("allBrands", coolingService.getAllBrands());
        return "/build/cooling";
    }

    @PostMapping("/cooling/filter")
    public String filterCoolings(@RequestParam(required = false) List<String> brands,
                                 @RequestParam(required = false) String sortBy,
                                 @ModelAttribute("buildItems") BuildItemDto buildItem,
                                 Model model) {
        List<Cooling> coolings = buildService.getCompatibleCoolings(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        coolings = coolingService.filterCoolings(coolings, filters, sortBy);

        model.addAttribute("coolings", coolings);
        model.addAttribute("allBrands", coolingService.getAllBrands());
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "/build/cooling";
    }

    @PostMapping("/selectCooling")
    public String selectCooling(@RequestParam(value = "coolingId", required = false) Integer coolingId,
                                @ModelAttribute("buildItems") BuildItemDto buildItem) {
        if (coolingId != null) {
            buildItem.setCooling(coolingService.getCoolingById(coolingId));
        }
        return "redirect:/build/memory";
    }
}
