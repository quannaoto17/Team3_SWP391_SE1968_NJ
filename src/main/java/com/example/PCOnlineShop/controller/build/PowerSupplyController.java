package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.PowerSupply;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.PowerSupplyService;
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
        model.addAttribute("allBrands", powerSupplyService.getAllBrands());
        return "/build/psu";
    }

    @PostMapping("/psu/filter")
    public String filterPowerSupplies(@RequestParam(required = false) List<String> brands,
                                      @RequestParam(required = false) String sortBy,
                                      @ModelAttribute("buildItems") BuildItemDto buildItem,
                                      Model model) {
        List<PowerSupply> powerSupplies = buildService.getCompatiblePowerSupplies(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        powerSupplies = powerSupplyService.filterPowerSupplies(powerSupplies, filters, sortBy);

        model.addAttribute("psus", powerSupplies);
        model.addAttribute("allBrands", powerSupplyService.getAllBrands());
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "/build/psu";
    }

    @PostMapping("/selectPsu")
    public String selectPsu(@RequestParam int psuId,
                            @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setPowerSupply(powerSupplyService.getPowerSupplyById(psuId));
        return "redirect:/build/other";
    }
}
