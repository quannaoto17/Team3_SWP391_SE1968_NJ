package com.example.PCOnlineShop.controller.build;

// DISABLED: "Other" component has been removed from the build system
// PSU is now the last component before finishing the build

/*
import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Other;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.service.build.BuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/build")
@SessionAttributes({"buildItems"})
public class OtherController {

    private final BuildService buildService;

    public OtherController(BuildService buildService) {
        this.buildService = buildService;
    }

    @GetMapping("/other")
    public String showOther(Model model, @ModelAttribute("buildItems") BuildItemDto buildItems) {
        List<Product> others = buildService.getOtherProducts();
        model.addAttribute("others", others != null ? others : new ArrayList<>());
        model.addAttribute("buildItems", buildItems);
        // 'message' is already available in the model if set as a flash attribute
        return "build/other";
    }

    @PostMapping("/selectOther")
    public String selectOther(@RequestParam(value = "otherId", required = false) Integer otherId,
                              @ModelAttribute("buildItems") BuildItemDto buildItems,
                              RedirectAttributes redirectAttributes) {
        if (otherId == null) {
            redirectAttributes.addFlashAttribute("message", "Please select a product before finishing the build.");
            return "redirect:/build/other";
        }

        Optional<Other> otherOpt = buildService.findOtherByProductId(otherId);
        if (otherOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Selected product not found.");
            return "redirect:/build/other";
        }

        buildItems.setOther(otherOpt.get());
        // buildItems is session-managed; no need to explicitly set in HttpSession when using @SessionAttributes

        redirectAttributes.addFlashAttribute("message", "Other component added to build. Finalizing...");
        return "redirect:/build/finish";
    }
}
*/
