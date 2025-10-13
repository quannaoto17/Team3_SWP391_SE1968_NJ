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
@SessionAttributes({"buildItems"})
public class CaseController {

    @Autowired
    private CaseService caseService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    // Hiển thị danh sách case tương thích
    @GetMapping("/case")
    public String showCases(Model model, @ModelAttribute("buildItems") BuildItemDto buildItem) {
        List<Case> compatibleCases = caseService.getAllCompatibleCases(buildItem);
        model.addAttribute("cases", compatibleCases);
        return "build/cases";
    }

    // Chọn case
    @PostMapping("/selectCase")
    public String selectCase(@RequestParam("caseId") int caseId,
                           @ModelAttribute("buildItems") BuildItemDto buildItem) {
        Case selectedCase = caseService.selectCase(caseId);
        buildItem.setPcCase(selectedCase);
        return "redirect:/build/cooling";
    }

    // Lọc case theo form factor
    @GetMapping("/filterCases")
    @ResponseBody
    public List<Case> filterCases(
            @RequestParam(required = false) String formFactor,
            @ModelAttribute("buildItems") BuildItemDto buildItem) {
        List<Case> compatibleCases = caseService.getAllCompatibleCases(buildItem);
        return caseService.filterCasesByFormFactor(compatibleCases, formFactor);
    }
}
