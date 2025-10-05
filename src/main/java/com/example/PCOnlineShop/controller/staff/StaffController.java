package com.example.PCOnlineShop.controller.staff;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.staff.StaffService;
import org.springframework.data.domain.Page;
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

    // Danh sách nhân viên
    @GetMapping("/list")
    public String listStaff(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Page<Account> staffPage = staffService.getStaffPage(page, size);
        model.addAttribute("staffPage", staffPage);
        return "staff/staff-list";
    }


    // Form thêm nhân viên
    @GetMapping("/add")
    public String addStaffForm(Model model) {
        Account account = new Account();           // tạo object
        account.setRole(RoleName.Staff);           // ✅ gán mặc định Staff
        model.addAttribute("account", account);    // add vào model
        return "staff/add-staff";
    }


    // Lưu nhân viên
    @PostMapping("/add")
    public String saveStaff(@ModelAttribute("account") Account account) {
        account.setRole(RoleName.Staff); // Ép role thành Staff (thêm dòng này)
        staffService.saveStaff(account);
        return "redirect:/staff/list";
    }


    // Form edit
    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/edit-staff";
    }

    // Update nhân viên
    @PostMapping("/edit")
    public String updateStaff(@ModelAttribute("account") Account account) {
        staffService.saveStaff(account);
        return "redirect:/staff/list";
    }

    // Chuyển nhân viên sang Inactive thay vì xóa
    @GetMapping("/delete/{id}")
    public String deactivateStaff(@PathVariable int id) {
        staffService.deactivateStaff(id);
        return "redirect:/staff/list";
    }
}
