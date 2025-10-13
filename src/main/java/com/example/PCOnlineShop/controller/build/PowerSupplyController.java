package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.PowerSupplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class PowerSupplyController {
    private final PowerSupplyService powerSupplyService;
    private final BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/psu")
    public String showPsuPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("psus", buildService.getCompatiblePowerSupplies(buildItem));
        return "/build/psu";
    }

    @PostMapping("/selectPsu")
    public String selectPsu(@RequestParam int psuId,
                            @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setPowerSupply(powerSupplyService.getPowerSupplyById(psuId));
        return "redirect:/build/other";
    }
}
