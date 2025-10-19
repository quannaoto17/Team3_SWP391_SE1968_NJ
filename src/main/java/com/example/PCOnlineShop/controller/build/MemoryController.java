package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.Memory;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.MemoryService;
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
public class MemoryController {
    private final MemoryService memoryService;
    private final BuildService buildService;

    @ModelAttribute("buildItems")
    public BuildItemDto buildItems() {
        return new BuildItemDto();
    }

    @GetMapping("/memory")
    public String showMemoryPage(@ModelAttribute("buildItems") BuildItemDto buildItem, Model model) {
        model.addAttribute("memories", buildService.getCompatibleMemory(buildItem));
        model.addAttribute("allBrands", memoryService.getAllBrands());
        return "/build/memory";
    }

    @PostMapping("/memory/filter")
    public String filterMemories(@RequestParam(required = false) List<String> brands,
                                 @RequestParam(required = false) String sortBy,
                                 @ModelAttribute("buildItems") BuildItemDto buildItem,
                                 Model model) {
        List<Memory> memories = buildService.getCompatibleMemory(buildItem);
        Map<String,List<String>> filters = new HashMap<>();
        filters.put("brands", brands);
        memories = memoryService.filterMemories(memories, filters, sortBy);

        model.addAttribute("memories", memories);
        model.addAttribute("allBrands", memoryService.getAllBrands());
        model.addAttribute("selectedBrands", brands);
        model.addAttribute("selectedSort", sortBy);
        return "/build/memory";
    }

    @PostMapping("/selectMemory")
    public String selectMemory(@RequestParam(value = "memoryId", required = false) Integer memoryId,
                               @ModelAttribute("buildItems") BuildItemDto buildItem) {
        if (memoryId != null) {
            buildItem.setMemory(memoryService.getMemoryById(memoryId));
        }
        return "redirect:/build/storage";
    }
}
