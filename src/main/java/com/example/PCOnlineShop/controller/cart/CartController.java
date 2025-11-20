package com.example.PCOnlineShop.controller.cart;

import com.example.PCOnlineShop.dto.cart.CartSummaryDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.cart.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final AccountRepository accountRepository;

    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return "redirect:/auth/login";

        CartSummaryDTO cartSummary = cartService.getCartDetails(account);

        model.addAttribute("isEmpty", cartSummary.getItems().isEmpty());
        model.addAttribute("cartItems", cartSummary.getItems());
        model.addAttribute("grandTotal", cartSummary.getSelectedTotal());
        return "cart/view";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable int productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @AuthenticationPrincipal UserDetails currentUser,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return "redirect:/auth/login?required";

        try {
            cartService.addToCart(account, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Product added to cart!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:" + (request.getHeader("Referer") != null ? request.getHeader("Referer") : "/");
    }

    @GetMapping("/addListItem")
    public String addListItemToCart(@ModelAttribute("productIds") List<Integer> productIds,
                                    @RequestParam(defaultValue = "1") int quantity,
                                    @AuthenticationPrincipal UserDetails currentUser,
                                    RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return "redirect:/auth/login?required";

        if (productIds == null || productIds.isEmpty()) {
            return "redirect:/build/start";
        }

        try {
            cartService.addListToCart(account, productIds, quantity);
            redirectAttributes.addFlashAttribute("success", "Products added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/update/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> updateQuantityAjax(@PathVariable int cartItemId,
                                                @RequestParam int quantity,
                                                @AuthenticationPrincipal UserDetails currentUser) {
        return handleCartAction(currentUser, () -> cartService.updateQuantity(getCurrentAccount(currentUser), cartItemId, quantity));
    }

    @PostMapping("/remove/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> removeFromCartAjax(@PathVariable int cartItemId,
                                                @AuthenticationPrincipal UserDetails currentUser) {
        return handleCartAction(currentUser, () -> cartService.removeFromCart(getCurrentAccount(currentUser), cartItemId));
    }

    @PostMapping("/select/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> selectItem(@PathVariable int cartItemId,
                                        @AuthenticationPrincipal UserDetails currentUser) {
        return handleCartAction(currentUser, () -> cartService.toggleSelectItem(getCurrentAccount(currentUser), cartItemId, true));
    }

    @PostMapping("/deselect/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> deselectItem(@PathVariable int cartItemId,
                                          @AuthenticationPrincipal UserDetails currentUser) {
        return handleCartAction(currentUser, () -> cartService.toggleSelectItem(getCurrentAccount(currentUser), cartItemId, false));
    }

    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        if (account != null) {
            cartService.clearCart(account);
            redirectAttributes.addFlashAttribute("success", "Cart cleared.");
        }
        return "redirect:/cart";
    }

    private ResponseEntity<?> handleCartAction(UserDetails currentUser, Runnable action) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in."));
        try {
            action.run();
            double newTotal = cartService.calculateSelectedTotalForAccount(account);
            return ResponseEntity.ok(Map.of("message", "Success", "newGrandTotal", newTotal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}