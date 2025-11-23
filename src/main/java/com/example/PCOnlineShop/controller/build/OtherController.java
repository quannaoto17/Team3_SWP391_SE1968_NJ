package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.model.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/build")
@SessionAttributes("buildItems")
public class OtherController {

    private final BuildService buildService;

    public OtherController(BuildService buildService) {
        this.buildService = buildService;
    }

    @ModelAttribute("buildItems")
    public BuildItemDto getBuildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/other")
    public String showOtherPage(@ModelAttribute("buildItems") BuildItemDto buildItems, Model model) {
        List<Product> others = buildService.getOtherProducts();
        model.addAttribute("others", others != null ? others : new ArrayList<>());
        return "build/other";
    }

    @PostMapping("/selectOther")
    public String selectOther(@RequestParam(value = "otherId", required = false) Integer otherId,
                              @ModelAttribute("buildItems") BuildItemDto buildItems,
                              RedirectAttributes redirectAttributes) {
        if (otherId == null) {
            buildItems.setOther(null);
            redirectAttributes.addFlashAttribute("message", "Please select a product before finishing the build.");
            return "redirect:/build/other";
        }

        Optional<Product> otherOpt = buildService.findOtherByProductId(otherId);
        if (otherOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Product not found.");
            return "redirect:/build/other";
        }

        buildItems.setOther(otherOpt.get());
        redirectAttributes.addFlashAttribute("message", "Other component selected successfully.");
        return "redirect:/build/other";
    }
}
