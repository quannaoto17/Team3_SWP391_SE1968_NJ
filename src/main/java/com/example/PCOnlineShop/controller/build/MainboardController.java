package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Mainboard;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.MainboardService;
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
public class MainboardController {
    private final MainboardService mainboardService;
    private  final BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    // Hiển thị danh sách motherboard
    @GetMapping("/mainboard")
    public String showMainboardPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("mainboards", buildService.getCompatibleMainboards(buildItem));
        model.addAttribute("allBrands", mainboardService.getAllBrands(buildItem));
        return "build/mainboards";
    }

    // Filter motherboard by brands
    @PostMapping("/mainboard/filter")
    public String filterMainboards(@RequestParam(required = false) List<String> brands,
                                   @RequestParam(required = false) String sortBy,
                                   @ModelAttribute("buildItems") BuildItemDto buildItem,
                                   Model model) {
        List<Mainboard> mainboards = buildService.getCompatibleMainboards(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        mainboards = mainboardService.filterMainboards(mainboards, filters, sortBy);


        model.addAttribute("mainboards", mainboards);
        model.addAttribute("allBrands", mainboardService.getAllBrands(buildItem));
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "build/mainboards";
    }

    // Hiển thị chi tiết motherboard
    @GetMapping("/mainboard/{id}")
    public String showMainboardDetail(@PathVariable int id, Model model) {
        Mainboard mainboard = mainboardService.getMainboardById(id);
        model.addAttribute("mainboard", mainboard);
        return "/build/mainboard/mainboard-detail";
    }

    //Chọn motherboard
    @PostMapping("/selectMainboard")
    public String selectMainboard(@RequestParam(required = false) Integer mainboardId,
                                  @ModelAttribute("buildItems") BuildItemDto buildItem,
                                  org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        // Mainboard is REQUIRED - must select one
        if (mainboardId == null && buildItem.getMainboard() == null) {
            redirectAttributes.addFlashAttribute("error", "Please select a mainboard to continue.");
            return "redirect:/build/mainboard";
        }

        // Only update if user selected a new mainboard
        if (mainboardId != null) {
            buildItem.setMainboard(mainboardService.getMainboardById(mainboardId));
        }
        // If mainboardId is null but buildItem.mainboard exists, keep it
        return "redirect:/build/cpu";
    }
    // Thêm, sửa, xóa motherboard sẽ do admin thực hiện qua trang admin
}
