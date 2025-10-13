package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class BuildController {
    @ModelAttribute("buildItems")
    public BuildItemDto buildItem() {
        return new BuildItemDto();
    }

    @GetMapping("/start" )
    public String startBuild() {
        return "/build/build-pc";
    }

    @GetMapping("/startover")
    public String startOver(HttpSession session) {
        session.removeAttribute("buildItems"); // Chỉ xóa buildItems khỏi session
        return "redirect:/build/mainboard";
    }
}
