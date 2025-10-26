package com.example.PCOnlineShop.controller.cart;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.account.Account; // Import Account
import com.example.PCOnlineShop.repository.account.AccountRepository; // Import AccountRepository
import com.example.PCOnlineShop.service.cart.CartService; // Import CartService
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // Import
import org.springframework.security.core.userdetails.UserDetails;          // Import
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
    private final AccountRepository accountRepository; // Inject để lấy Account từ UserDetails

    // --- Helper lấy Account từ UserDetails ---
    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) {
            return null; // Chưa đăng nhập
        }
        // Giả sử username là phoneNumber
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    // --- GET /cart: Hiển thị trang giỏ hàng ---
    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return "redirect:/auth/login"; // Yêu cầu đăng nhập
        }

        List<CartItemDTO> cartItems = cartService.getCartItems(account);
        double grandTotal = cartService.calculateGrandTotal(cartItems);

        model.addAttribute("isEmpty", cartItems.isEmpty());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", grandTotal);
        return "cart/view";
    }

    // --- POST /cart/add/{productId}: Thêm sản phẩm ---
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable int productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @AuthenticationPrincipal UserDetails currentUser,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request) {

        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return "redirect:/auth/login";
        }

        try {
            cartService.addToCart(account, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Product added to cart!");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred.");
            System.err.println("Error adding to cart: " + e.getMessage()); // Log lỗi
        }
        return "redirect:" + getPreviousPage(request);
    }

    // --- POST /cart/update/{productId}: Cập nhật số lượng ---
    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable int productId,
                                 @RequestParam int quantity,
                                 @AuthenticationPrincipal UserDetails currentUser,
                                 RedirectAttributes redirectAttributes) {

        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return "redirect:/auth/login";
        }

        try {
            cartService.updateQuantity(account, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart quantity updated.");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred.");
            System.err.println("Error updating cart quantity: " + e.getMessage());
        }
        return "redirect:/cart";
    }


    // --- POST /cart/remove/{productId}: Xóa một sản phẩm ---
    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable int productId,
                                 @AuthenticationPrincipal UserDetails currentUser,
                                 RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return "redirect:/auth/login";
        }

        try {
            cartService.removeFromCart(account, productId);
            redirectAttributes.addFlashAttribute("success", "Product removed from cart.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred.");
            System.err.println("Error removing from cart: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    // --- POST /cart/clear: Xóa toàn bộ giỏ hàng ---
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails currentUser,
                            RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return "redirect:/auth/login";
        }
        try {
            cartService.clearCart(account);
            redirectAttributes.addFlashAttribute("success", "Cart cleared successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred while clearing the cart.");
            System.err.println("Error clearing cart: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    // --- Helper lấy URL trang trước ---
    private String getPreviousPage(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer != null && referrer.contains("/cart")) {
            return "/cart";
        }
        return (referrer != null && !referrer.isEmpty()) ? referrer : "/";
    }
}