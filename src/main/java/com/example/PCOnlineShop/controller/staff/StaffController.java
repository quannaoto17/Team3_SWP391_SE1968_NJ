package com.example.PCOnlineShop.controller.staff;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import com.example.PCOnlineShop.service.staff.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// StaffController.java
@Controller
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private final AuthService authService;

    @GetMapping("/list")
    public String listStaff(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "") String searchQuery,
                            @RequestParam(defaultValue = "active") String statusFilter,
                            Model model) {

        int safePage = Math.max(page, 1);
        int zeroBasedPage = safePage - 1;

        Page<Account> staffPage = staffService.getStaffPage(zeroBasedPage, size, searchQuery, statusFilter);

        model.addAttribute("staffPage", staffPage);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("statusFilter", statusFilter);
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", staffPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "staff/staff-list";
    }

    @GetMapping("/view/{id}")
    public String viewStaff(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/view-staff";
    }

    @GetMapping("/add")
    public String addStaffForm(Model model) {
        model.addAttribute("account", new Account());
        return "staff/add-staff";
    }

    // ⬇️ Lưu + địa chỉ
    @PostMapping("/add")
    public String saveStaff(@Valid @ModelAttribute("account") Account account,
                            BindingResult result,
                            @RequestParam(name = "addressStr", required = false) String addressStr,
                            Model model) {
        if (result.hasErrors()) {
            return "staff/add-staff";
        }

        try {
            Account saved = authService.saveStaff(account);
            staffService.saveDefaultAddress(saved, addressStr); // ⬅️ lưu địa chỉ
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "staff/add-staff";
        }

        return "redirect:/staff/list?statusFilter=all";
    }

    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/edit-staff";
    }

    // ⬇️ Cập nhật + địa chỉ
    @PostMapping("/edit")
    public String updateStaff(@Valid @ModelAttribute("account") Account account,
                              BindingResult result,
                              @RequestParam(name = "addressStr", required = false) String addressStr,
                              Model model) {
        if (result.hasErrors()) {
            return "staff/edit-staff";
        }

        try {
            Account saved = authService.saveStaff(account);
            staffService.updateDefaultAddress(saved, addressStr); // ⬅️ cập nhật địa chỉ
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "staff/edit-staff";
        }

        return "redirect:/staff/list?statusFilter=all";
    }

    @GetMapping("/delete/{id}")
    public String deactivateStaff(@PathVariable int id) {
        staffService.deactivateStaff(id);
        return "redirect:/staff/list?statusFilter=all";
    }
}
