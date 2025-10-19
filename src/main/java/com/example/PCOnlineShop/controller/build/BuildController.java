package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

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
        session.removeAttribute("buildItems"); // Clear buildItems from session to start a fresh build without affecting user login session or other session data
        return "redirect:/build/mainboard";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/finish")
    public String finishBuild(@ModelAttribute("buildItems") BuildItemDto buildItems,
                              HttpSession session,
                              SessionStatus sessionStatus,
                              RedirectAttributes redirectAttributes) {
        // prepare session cartBuilds
        List<BuildItemDto> cartBuilds = (List<BuildItemDto>) session.getAttribute("cartBuilds");
        if (cartBuilds == null) cartBuilds = new ArrayList<>();

        // deep copy to avoid later mutation of component objects in the cart
        BuildItemDto toAdd = new BuildItemDto(
                buildItems.getMainboard() != null ? buildItems.getMainboard().clone() : null,
                buildItems.getCpu() != null ? buildItems.getCpu().clone() : null,
                buildItems.getMemory() != null ? buildItems.getMemory().clone() : null,
                buildItems.getGpu() != null ? buildItems.getGpu().clone() : null,
                buildItems.getStorage() != null ? buildItems.getStorage().clone() : null,
                buildItems.getPowerSupply() != null ? buildItems.getPowerSupply().clone() : null,
                buildItems.getPcCase() != null ? buildItems.getPcCase().clone() : null,
                buildItems.getCooling() != null ? buildItems.getCooling().clone() : null,
                buildItems.getOther() != null ? buildItems.getOther().clone() : null
        );

        cartBuilds.add(toAdd);
        session.setAttribute("cartBuilds", cartBuilds);

        // Clear only the session-managed buildItems using SessionStatus
        sessionStatus.setComplete();

        redirectAttributes.addFlashAttribute("message", "Build added to prepared cart.");
        // redirect to cart preview (placeholder)
        return "redirect:/cart";
    }
}
