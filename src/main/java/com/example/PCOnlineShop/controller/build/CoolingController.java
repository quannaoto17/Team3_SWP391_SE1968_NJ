package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CoolingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "/build/cooling";
    }

    @PostMapping("/selectCooling")
    public String selectCooling(@RequestParam int coolingId,
                                @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setCooling(coolingService.getCoolingById(coolingId));
        return "redirect:/build/memory";
    }
}
