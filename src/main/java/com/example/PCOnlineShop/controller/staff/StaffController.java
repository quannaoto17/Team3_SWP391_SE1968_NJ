package com.example.PCOnlineShop.controller.staff;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import com.example.PCOnlineShop.service.staff.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private final AuthService authService;

    @GetMapping("/list")
    public String listStaff(@RequestParam(defaultValue = "active") String statusFilter,
                            Model model) {
        // Lấy toàn bộ danh sách khách hàng theo trạng thái (active/inactive)
        List<Account> staffList = staffService.getAllStaff(statusFilter);
        // Gửi dữ liệu sang view
        model.addAttribute("staffList", staffList);
        model.addAttribute("statusFilter", statusFilter);

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

    @PostMapping("/add")
    public String saveStaff(@Valid @ModelAttribute("account") Account account,
                            BindingResult result,
                            @RequestParam(name = "addressStr", required = false) String addressStr,
                            Model model) {
        // Nếu form có lỗi validate → quay lại trang add
        if (result.hasErrors()) return "staff/add-staff";

        try {
            // Lưu tài khoản staff (bao gồm check email/phone trùng, mã hóa password,…)
            Account saved = authService.saveStaff(account);
            staffService.saveDefaultAddress(saved, addressStr);
        } catch (IllegalArgumentException e) {
            // Các lỗi như email trùng, SĐT trùng → báo lỗi
            model.addAttribute("errorMessage", e.getMessage());
            return "staff/add-staff";
        }

        return "redirect:/staff/list";
    }

    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/edit-staff";
    }

    @PostMapping("/edit")
    public String updateStaff(@Valid @ModelAttribute("account") Account account,
                              BindingResult result,
                              @RequestParam(name = "addressStr", required = false) String addressStr,
                              Model model) {
        // Nếu form lỗi validation → quay lại form add
        if (result.hasErrors()) return "staff/edit-staff";

        try {
            Account saved = authService.saveStaff(account);
            staffService.updateDefaultAddress(saved, addressStr);
        } catch (IllegalArgumentException e) {
            // Lỗi như: Email đã tồn tại, SĐT đã tồn tại,...
            model.addAttribute("errorMessage", e.getMessage());
            return "staff/edit-staff";
        }
        return "redirect:/staff/list";
    }

    @GetMapping("/delete/{id}")
    public String deactivateStaff(@PathVariable int id) {
        staffService.deactivateStaff(id);
        return "redirect:/staff/list";
    }
}
