package com.example.PCOnlineShop.controller.cart;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.cart.CartService;
import jakarta.persistence.EntityNotFoundException;
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

        List<CartItemDTO> cartItems = cartService.getCartItems(account);

        double grandTotal = cartService.calculateSelectedTotal(cartItems);

        model.addAttribute("isEmpty", cartItems.isEmpty());

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", grandTotal);
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
        return "redirect:" + getPreviousPage(request);
    }

    @GetMapping("/addListItem")
    public String addListItemToCart(@ModelAttribute("productIds") List<Integer> productIds,
                                    @RequestParam(defaultValue = "1") int quantity,
                                    @AuthenticationPrincipal UserDetails currentUser,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request) {

        Account account = getCurrentAccount(currentUser);
        if (account == null) return "redirect:/auth/login?required";

        if (productIds == null || productIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No products to add.");
            return "redirect:/build/start";
        }

        try {
            cartService.addListToCart(account, productIds, quantity);
            redirectAttributes.addFlashAttribute("success", "Products added to cart!");
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
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in."));
        }
        try {
            cartService.updateQuantity(account, cartItemId, quantity);

            List<CartItemDTO> updatedItems = cartService.getCartItems(account);
            double newSelectedTotal = cartService.calculateSelectedTotal(updatedItems);

            return ResponseEntity.ok(Map.of(
                    "message", "Quantity updated.",
                    "newGrandTotal", newSelectedTotal
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/remove/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> removeFromCartAjax(@PathVariable int cartItemId,
                                                @AuthenticationPrincipal UserDetails currentUser) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in."));
        }
        try {
            cartService.removeFromCart(account, cartItemId);

            List<CartItemDTO> updatedItems = cartService.getCartItems(account);
            double newSelectedTotal = cartService.calculateSelectedTotal(updatedItems);

            return ResponseEntity.ok(Map.of(
                    "message", "Product removed.",
                    "newGrandTotal", newSelectedTotal
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // CÁC ENDPOINT CHO LOGIC "CHỌN"

    @PostMapping("/select/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> selectItem(@PathVariable int cartItemId,
                                        @AuthenticationPrincipal UserDetails currentUser) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        cartService.toggleSelectItem(account, cartItemId, true);

        List<CartItemDTO> updatedItems = cartService.getCartItems(account);
        double newSelectedTotal = cartService.calculateSelectedTotal(updatedItems);
        return ResponseEntity.ok(Map.of("status", "selected", "newGrandTotal", newSelectedTotal));
    }

    @PostMapping("/deselect/{cartItemId}")
    @ResponseBody
    public ResponseEntity<?> deselectItem(@PathVariable int cartItemId,
                                          @AuthenticationPrincipal UserDetails currentUser) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        cartService.toggleSelectItem(account, cartItemId, false);

        List<CartItemDTO> updatedItems = cartService.getCartItems(account);
        double newSelectedTotal = cartService.calculateSelectedTotal(updatedItems);
        return ResponseEntity.ok(Map.of("status", "deselected", "newGrandTotal", newSelectedTotal));
    }

    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return "redirect:/auth/login";
        try {
            cartService.clearCart(account);
            redirectAttributes.addFlashAttribute("success", "Cart cleared successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error clearing cart.");
        }
        return "redirect:/cart";
    }

    private String getPreviousPage(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer != null && referrer.contains("/cart")) {
            return "/cart";
        }
        return (referrer != null && !referrer.isEmpty()) ? referrer : "/";
    }
}