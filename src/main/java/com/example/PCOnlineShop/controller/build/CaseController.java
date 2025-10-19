package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Case;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/build")
@SessionAttributes({"buildItems"})
public class CaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/case")
    public String showCases(Model model, @ModelAttribute("buildItems") BuildItemDto buildItem) {
        model.addAttribute("cases", buildService.getCompatibleCases(buildItem));
        model.addAttribute("allBrands", caseService.getAllBrands());
        return "build/cases";
    }

    @PostMapping("/case/filter")
    public String filterCases(@RequestParam(required = false) List<String> brands,
                              @RequestParam(required = false) String sortBy,
                              @ModelAttribute("buildItems") BuildItemDto buildItem,
                              Model model) {
        List<Case> cases = buildService.getCompatibleCases(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        cases = caseService.filterCases(cases, filters, sortBy);

        model.addAttribute("cases", cases);
        model.addAttribute("allBrands", caseService.getAllBrands());
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "build/cases";
    }

    @PostMapping("/selectCase")
    public String selectCase(@RequestParam(value = "caseId", required = false) Integer caseId,
                           @ModelAttribute("buildItems") BuildItemDto buildItem) {
        if (caseId != null) {
            buildItem.setPcCase(caseService.getCaseById(caseId));
        }
        return "redirect:/build/cooling";
    }
}
