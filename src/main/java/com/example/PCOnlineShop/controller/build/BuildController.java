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

    @GetMapping("/preset-result")
    public String showPresetResult() {
        return "/build/preset-result";
    }

    @GetMapping("/startover")
    public String startOver(SessionStatus sessionStatus, org.springframework.ui.Model model) {
        // Tell Spring to clear the session-managed `buildItems`
        sessionStatus.setComplete();

        // Provide a fresh empty DTO so the next request's model is clean
        model.addAttribute("buildItems", new BuildItemDto());

        // Redirect to the first build page (avoids reusing form values)
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

        // copy to avoid later mutation of component objects in the cart
        BuildItemDto toAdd = new BuildItemDto(
                buildItems.getMainboard(),
                buildItems.getCpu(),
                buildItems.getMemory(),
                buildItems.getGpu(),
                buildItems.getStorage(),
                buildItems.getPowerSupply(),
                buildItems.getPcCase(),
                buildItems.getCooling(),
                buildItems.getOther()
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
