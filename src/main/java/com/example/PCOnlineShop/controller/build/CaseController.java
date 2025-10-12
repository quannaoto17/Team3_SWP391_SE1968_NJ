package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Case;
import com.example.PCOnlineShop.service.build.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/build")
@SessionAttributes("buildItem")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @ModelAttribute("buildItem")
    public BuildItemDto buildItem() {
        return new BuildItemDto();
    }

    @GetMapping("/case")
    public String showCases(Model model, @ModelAttribute("buildItem") BuildItemDto buildItem) {
        List<Case> compatibleCases = caseService.getAllCompatibleCases(buildItem);
        model.addAttribute("cases", compatibleCases);
        return "build/cases";
    }

    @PostMapping("/selectCase")
    public String selectCase(@RequestParam("caseId") int caseId,
                           @ModelAttribute("buildItem") BuildItemDto buildItem) {
        Case selectedCase = caseService.selectCase(caseId);
        buildItem.setPcCase(selectedCase);
        return "redirect:/build/cooling";
    }

    @GetMapping("/filterCases")
    @ResponseBody
    public List<Case> filterCases(
            @RequestParam(required = false) String formFactor,
            @ModelAttribute("buildItem") BuildItemDto buildItem) {
        List<Case> compatibleCases = caseService.getAllCompatibleCases(buildItem);
        return caseService.filterCasesByFormFactor(compatibleCases, formFactor);
    }
}
