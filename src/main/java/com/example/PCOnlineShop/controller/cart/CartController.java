package com.example.PCOnlineShop.controller.cart;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.cart.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        double grandTotal = cartService.calculateGrandTotal(cartItems);

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
        if (account == null) return "redirect:/auth/login?required"; // Thêm param để báo lý do

        try {
            cartService.addToCart(account, productId, quantity);
            redirectAttributes.addFlashAttribute("success", "Product added to cart!");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred adding to cart.");
            System.err.println("Error adding to cart: " + e.getMessage());
        }
        return "redirect:" + getPreviousPage(request);
    }

    // --- Cập nhật số lượng (Dùng cho AJAX từ JS thuần) ---
    // Trả về JSON thay vì redirect
    @PostMapping("/update/{productId}")
    @ResponseBody // Đánh dấu trả về JSON
    public ResponseEntity<?> updateQuantityAjax(@PathVariable int productId,
                                                @RequestParam int quantity,
                                                @AuthenticationPrincipal UserDetails currentUser) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in."));
        }
        try {
            cartService.updateQuantity(account, productId, quantity);
            // Lấy lại thông tin giỏ hàng sau khi cập nhật để trả về tổng tiền mới
            List<CartItemDTO> updatedItems = cartService.getCartItems(account);
            double newGrandTotal = cartService.calculateGrandTotal(updatedItems);
            int updatedItemCount = updatedItems.size(); // Số lượng item mới

            // Trả về thông tin cần thiết cho JS cập nhật UI
            return ResponseEntity.ok(Map.of(
                    "message", "Quantity updated.",
                    "newGrandTotal", newGrandTotal,
                    "itemCount", updatedItemCount
            ));
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error updating cart quantity via AJAX: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Server error updating quantity."));
        }
    }

    // --- Xóa sản phẩm (Dùng cho AJAX từ JS thuần) ---
    // Trả về JSON
    @PostMapping("/remove/{productId}")
    @ResponseBody
    public ResponseEntity<?> removeFromCartAjax(@PathVariable int productId,
                                                @AuthenticationPrincipal UserDetails currentUser) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in."));
        }
        try {
            cartService.removeFromCart(account, productId);
            // Lấy lại thông tin giỏ hàng sau khi xóa
            List<CartItemDTO> updatedItems = cartService.getCartItems(account);
            double newGrandTotal = cartService.calculateGrandTotal(updatedItems);
            int updatedItemCount = updatedItems.size();

            return ResponseEntity.ok(Map.of(
                    "message", "Product removed.",
                    "newGrandTotal", newGrandTotal,
                    "itemCount", updatedItemCount
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error removing from cart via AJAX: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Server error removing item."));
        }
    }

    // --- POST /cart/clear: Xóa toàn bộ giỏ hàng (Vẫn dùng redirect) ---
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirectAttributes) {
        Account account = getCurrentAccount(currentUser);
        if (account == null) return "redirect:/auth/login";
        try {
            cartService.clearCart(account);
            redirectAttributes.addFlashAttribute("success", "Cart cleared successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error clearing cart.");
            System.err.println("Error clearing cart: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    private String getPreviousPage(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        // Ưu tiên quay về trang chứa /cart/ nếu đang ở đó
        if (referrer != null && referrer.contains("/cart")) {
            return "/cart";
        }
        return (referrer != null && !referrer.isEmpty()) ? referrer : "/";
    }
}