package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemId;
import com.example.PCOnlineShop.model.build.Mainboard;
import com.example.PCOnlineShop.service.build.MainboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class MainboardController {
    private final MainboardService mainboardService;

    @ModelAttribute("buildItems")
    public BuildItemId buildItem() {
        return new BuildItemId();
    }

    // Hiển thị danh sách motherboard
    @GetMapping("/mainboard")
    public String showMainboardPage(Model model) {
        model.addAttribute("mainboards", mainboardService.getAllMainboards());
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
    public String selectMainboard(@RequestParam int mainboardId,
                                  @ModelAttribute("buildItems") BuildItemId buildItemId) {
        buildItemId.setMainboardId(mainboardId);
        return "redirect:/build/cpu";
    }
    // Thêm, sửa, xóa motherboard sẽ do admin thực hiện qua trang admin
}
