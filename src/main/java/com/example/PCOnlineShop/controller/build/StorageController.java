package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class StorageController {
    private final StorageService storageService;
    private final BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/storage")
    public String showStoragePage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("storages", buildService.getCompatibleStorage(buildItem));
        return "/build/storage";
    }

    @PostMapping("/selectStorage")
    public String selectStorage(@RequestParam int storageId,
                                @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setStorage(storageService.getStorageById(storageId));
        return "redirect:/build/psu";
    }
}
