package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.ProductRepository;
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

    private final ProductRepository productRepository;

    public BuildController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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

        List<CartItemDTO> cartItems = new ArrayList<>();

        //Fetch LazyInitializationException
        if (buildItems.getMainboard() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getMainboard().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getMainboard().getProduct(), 1));
        }
        if (buildItems.getCpu() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getCpu().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getCpu().getProduct(), 1));
        }
        if (buildItems.getMemory() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getMemory().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getMemory().getProduct(), 1));
        }
        if (buildItems.getGpu() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getGpu().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getGpu().getProduct(), 1));
        }
        if (buildItems.getStorage() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getStorage().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getStorage().getProduct(), 1));
        }
        if (buildItems.getPowerSupply() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getPowerSupply().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getPowerSupply().getProduct(), 1));
        }
        if (buildItems.getPcCase() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getPcCase().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getPcCase().getProduct(), 1));
        }
        if (buildItems.getCooling() != null) {
            Product product = productRepository.findByIdWithImages(buildItems.getCooling().getProduct().getProductId());
            cartItems.add(new CartItemDTO(product != null ? product : buildItems.getCooling().getProduct(), 1));
        }

        session.setAttribute("cartBuilds", cartItems);

        // Clear only the session-managed buildItems using SessionStatus
        sessionStatus.setComplete();

        redirectAttributes.addFlashAttribute("message", "Build added to prepared cart.");
        // redirect to cart preview (placeholder)
        return "redirect:/orders/build-checkout";
    }
}
