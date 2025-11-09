package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Storage;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.StorageService;
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
public class StorageController {
    private final StorageService storageService;
    private final BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/storage")
    public String showStoragePage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        List<Storage> storages = buildService.getCompatibleStorage(buildItem);
        model.addAttribute("storages", storages);
        model.addAttribute("allBrands", storageService.getAllBrands(storages));
        return "/build/storage";
    }

    @PostMapping("/storage/filter")
    public String filterStorages(@RequestParam(required = false) List<String> brands,
                                 @RequestParam(required = false) String sortBy,
                                 @ModelAttribute("buildItems") BuildItemDto buildItem,
                                 Model model) {
        List<Storage> storages = buildService.getCompatibleStorage(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        storages = storageService.filterStorages(storages, filters, sortBy);

        model.addAttribute("storages", storages);
        model.addAttribute("allBrands", storageService.getAllBrands(buildService.getCompatibleStorage(buildItem)));
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "/build/storage";
    }

    @PostMapping("/selectStorage")
    public String selectStorage(@RequestParam(value = "storageId", required = false) Integer storageId,
                                @ModelAttribute("buildItems") BuildItemDto buildItem,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        // Storage is REQUIRED - must select one
        if (storageId == null && buildItem.getStorage() == null) {
            redirectAttributes.addFlashAttribute("error", "Please select storage to continue.");
            return "redirect:/build/storage";
        }

        // Only update if user selected new storage
        if (storageId != null) {
            buildItem.setStorage(storageService.getStorageById(storageId));
        }
        // If storageId is null but buildItem.storage exists, keep it
        return "redirect:/build/psu";
    }
}
