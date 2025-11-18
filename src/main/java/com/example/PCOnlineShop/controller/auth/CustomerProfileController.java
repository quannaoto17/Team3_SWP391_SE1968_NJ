package com.example.PCOnlineShop.controller.auth;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import com.example.PCOnlineShop.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class CustomerProfileController {

    private final CustomerService customerService;
    private final AuthService authService;

    // ======================== VIEW PROFILE ========================
    @GetMapping("")
    public String viewProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        String phoneNumber = principal.getName(); //
        Account account = authService.getByPhoneNumber(phoneNumber);

        if (account == null) {
            model.addAttribute("error", "Không tìm thấy tài khoản!");
            model.addAttribute("account", new Account()); // tránh lỗi form
            return "profile/view-profile";
        }

        model.addAttribute("account", account);
        return "profile/view-profile";
    }




    // ======================== UPDATE PROFILE ========================
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("account") Account updatedAccount,
                                Principal principal,
                                Model model) {
        String phoneNumber = principal.getName();
        Account existing = authService.getByPhoneNumber(phoneNumber);

        if (existing == null) {
            model.addAttribute("error", "Không tìm thấy tài khoản!");
            return "profile/view-profile";
        }

        // cập nhật các field cho phép
        existing.setFirstname(updatedAccount.getFirstname());
        existing.setLastname(updatedAccount.getLastname());
        existing.setPhoneNumber(updatedAccount.getPhoneNumber());
        existing.setGender(updatedAccount.getGender());

        authService.saveCustomer(existing);
        model.addAttribute("account", existing);
        model.addAttribute("success", "Cập nhật thông tin thành công!");

        return "profile/view-profile";
    }
}
