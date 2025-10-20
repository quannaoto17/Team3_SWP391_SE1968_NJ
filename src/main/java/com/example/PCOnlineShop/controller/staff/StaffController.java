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

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private final AuthService authService;

    //  Danh sách nhân viên
    @GetMapping("/list")
    public String listStaff(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "") String searchQuery,
                            @RequestParam(defaultValue = "all") String statusFilter,
                            Model model) {

        Page<Account> staffPage = staffService.getStaffPage(page, size, searchQuery, statusFilter);
        model.addAttribute("staffPage", staffPage);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("statusFilter", statusFilter);
        return "staff/staff-list";
    }

    //  Xem chi tiết
    @GetMapping("/view/{id}")
    public String viewStaff(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/view-staff";
    }

    //  Form thêm
    @GetMapping("/add")
    public String addStaffForm(Model model) {
        model.addAttribute("account", new Account());
        return "staff/add-staff";
    }

    // Lưu nhân viên (Validate)
    @PostMapping("/add")
    public String saveStaff(@Valid @ModelAttribute("account") Account account,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "staff/add-staff";
        }

        try {
            authService.saveStaff(account);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "staff/add-staff";
        }

        return "redirect:/staff/list?statusFilter=all";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/edit-staff";
    }

    // Cập nhật (Validate)
    @PostMapping("/edit")
    public String updateStaff(@Valid @ModelAttribute("account") Account account,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "staff/edit-staff";
        }

        try {
            authService.saveStaff(account);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "staff/edit-staff";
        }

        return "redirect:/staff/list?statusFilter=all";
    }

    //  Chuyển trạng thái
    @GetMapping("/delete/{id}")
    public String deactivateStaff(@PathVariable int id) {
        staffService.deactivateStaff(id);
        return "redirect:/staff/list?statusFilter=all";
    }
}
