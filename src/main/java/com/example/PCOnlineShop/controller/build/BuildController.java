package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.cart.CartService; // ✅ Tiêm CartService
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // ✅ Thêm
import org.springframework.security.core.userdetails.UserDetails; // ✅ Thêm
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class BuildController {

    private final CartService cartService;
    private final AccountRepository accountRepository;

    public BuildController(CartService cartService, AccountRepository accountRepository) {
        this.cartService = cartService;
        this.accountRepository = accountRepository;
    }

    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        if (userDetails instanceof Account) return (Account) userDetails;
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
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
    public String startOver(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("buildItems", new BuildItemDto());
        return "redirect:/build/mainboard";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/finish")
    public String finishBuild(@ModelAttribute("buildItems") BuildItemDto buildItems,
                              @AuthenticationPrincipal UserDetails currentUser,
                              SessionStatus sessionStatus,
                              RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        try {

            cartService.addBuildToCart(account, buildItems);

            // Xóa "buildItems" (DTO) khỏi session
            sessionStatus.setComplete();
            redirectAttributes.addFlashAttribute("success", "Bộ PC đã được thêm vào giỏ hàng!");
            return "redirect:/cart";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu PC Build: " + e.getMessage());
            return "redirect:/build/start";
        }
    }
}
