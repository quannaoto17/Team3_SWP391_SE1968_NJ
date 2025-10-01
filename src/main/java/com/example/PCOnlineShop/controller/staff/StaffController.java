package com.example.PCOnlineShop.controller.staff;

import com.example.PCOnlineShop.model.staff.Account;
import com.example.PCOnlineShop.model.staff.AccountDetail;
import com.example.PCOnlineShop.service.staff.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // Hiển thị danh sách staff
    @GetMapping("/list")
    public String listStaff(Model model) {
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff-list";
    }

    // Mở form Add
    @GetMapping("/add")
    public String addStaffForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("detail", new AccountDetail());
        return "add-staff";
    }

    // Lưu staff mới
    @PostMapping("/add")
    public String saveStaff(@ModelAttribute Account account,
                            @ModelAttribute AccountDetail detail) {
        staffService.addStaff(account, detail);
        return "redirect:/staff/list";
    }

    // Mở form Edit
    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable int id, Model model) {
        AccountDetail detail = staffService.getStaffById(id);
        model.addAttribute("detail", detail);
        model.addAttribute("account", detail.getAccount());
        return "edit-staff";
    }

    // Lưu edit
    @PostMapping("/edit")
    public String updateStaff(@ModelAttribute AccountDetail detail) {
        staffService.updateStaff(detail);
        return "redirect:/staff/list";
    }
}
