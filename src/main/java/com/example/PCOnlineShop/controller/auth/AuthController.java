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

    // B1. Hiển thị form nhập email/số điện thoại
    @GetMapping("/forget-password")
    public String showForgetPasswordPage() {
        return "auth/forget-password";
    }

    // B2. Xử lý gửi mã xác nhận qua email
    @PostMapping("/forget-password")
    public String processForgetPassword(@RequestParam("identifier") String identifier, Model model) {
        try {
            authService.sendResetCode(identifier);
            model.addAttribute("identifier", identifier);
            return "redirect:/auth/code-forget-password?identifier=" + identifier;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/forget-password";
        }
    }

    // B3. Hiển thị trang nhập mã xác nhận
    @GetMapping("/code-forget-password")
    public String showCodeForgetPassword(@RequestParam("identifier") String identifier, Model model) {
        model.addAttribute("identifier", identifier);
        return "auth/code-forget-password";
    }

    // B4. Xác thực mã xác nhận
    @PostMapping("/code-forget-password")
    public String verifyResetCode(@RequestParam("identifier") String identifier,
                                  @RequestParam("code") String code,
                                  Model model) {
        if (authService.verifyResetCode(identifier, code)) {
            return "redirect:/auth/reset-password?identifier=" + identifier;
        } else {
            model.addAttribute("error", "⚠️ Mã xác nhận không đúng hoặc đã hết hạn!");
            model.addAttribute("identifier", identifier);
            return "auth/code-forget-password";
        }
    }

    // B5. Hiển thị form đặt lại mật khẩu
    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam("identifier") String identifier, Model model) {
        model.addAttribute("identifier", identifier);
        return "auth/reset-password";
    }

    // B6. Xử lý đặt lại mật khẩu
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("identifier") String identifier,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "⚠️ Mật khẩu xác nhận không khớp!");
            model.addAttribute("identifier", identifier);
            return "auth/reset-password";
        }

        try {
            authService.resetPassword(identifier, newPassword);
            return "redirect:/auth/login?resetSuccess";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("identifier", identifier);
            return "auth/reset-password";
        }
    }
}
