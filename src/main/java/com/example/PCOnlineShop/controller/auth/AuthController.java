package com.example.PCOnlineShop.controller.auth;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 🔹 Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("account", new Account());
        return "auth/register";
    }

    // 🔹 Xử lý đăng ký
    @PostMapping("/register")
    public String register(@ModelAttribute("account") Account account,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {

        // ✅ Kiểm tra xác nhận mật khẩu
        if (!account.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "⚠️ Mật khẩu xác nhận không khớp!");
            return "auth/register";
        }

        try {
            authService.register(account);  // 🔹 Gọi service để lưu vào DB
            return "redirect:/auth/login?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // 🔹 Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }
}
