package com.example.PCOnlineShop.controller.auth;

import com.example.PCOnlineShop.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "auth/register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }
}
