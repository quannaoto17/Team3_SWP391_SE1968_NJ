package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.build.BuildService;
import com.example.PCOnlineShop.service.build.MemoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "/build/memory";
    }

    @PostMapping("/selectMemory")
    public String selectMemory(@RequestParam int memoryId,
                               @ModelAttribute("buildItems") BuildItemDto buildItem) {
        buildItem.setMemory(memoryService.getMemoryById(memoryId));
        return "redirect:/build/storage";
    }
}
