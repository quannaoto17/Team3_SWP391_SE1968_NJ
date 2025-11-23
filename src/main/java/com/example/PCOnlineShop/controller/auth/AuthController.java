package com.example.PCOnlineShop.controller.auth;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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
                           @RequestParam("address") String addressStr,
                           @RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes,
                           Model model) {

        if (!account.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "⚠️ Mật khẩu xác nhận không khớp!");
            return "auth/register";
        }

        try {
            authService.register(account, addressStr);  // 🔹 Gọi service để lưu vào DB
            redirectAttributes.addFlashAttribute("phoneNumber", account.getPhoneNumber());
            redirectAttributes.addFlashAttribute("password", account.getPassword());
            return "redirect:/auth/verify?email=" + account.getEmail();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("verify")
    public String verify(@RequestParam("email") String email, Model model) {
       try {
        this.authService.sendVerifyCode(email);
        return "auth/verify";
    } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        return "auth/register";
    }
    }

    @PostMapping("verify")
    public String verify(@RequestParam("email") String email, String code, Model model) {
        try {
            this.authService.verifyAccount(email, code);
            return "redirect:/auth/login?success";
        } catch (Exception ex){
            model.addAttribute("error", "⚠️ Mã xác nhận không đúng hoặc đã hết hạn!");
            model.addAttribute("email", email);
            return "auth/verify";
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
    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model,
                                 Principal principal) {
        String phoneNumber = principal.getName();

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("pwdError", "⚠️ Mật khẩu mới không khớp!");
            model.addAttribute("account", authService.getByPhoneNumber(phoneNumber));
            return "profile/view-profile";
        }

        boolean success = authService.changePassword(phoneNumber, currentPassword, newPassword);

        if (success) {
            model.addAttribute("pwdSuccess", "✅ Đổi mật khẩu thành công!");
        } else {
            model.addAttribute("pwdError", "⚠️ Mật khẩu hiện tại không đúng!");
        }

        // Load lại thông tin account để hiển thị
        model.addAttribute("account", authService.getByPhoneNumber(phoneNumber));
        return "profile/view-profile";
    }

}
