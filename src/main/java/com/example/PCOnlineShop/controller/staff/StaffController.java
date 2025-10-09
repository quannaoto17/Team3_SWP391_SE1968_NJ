package com.example.PCOnlineShop.controller.staff;

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

    // 🔹 Danh sách nhân viên (lọc theo trạng thái Active / Inactive / All)
    @GetMapping("/list")
    public String listStaff(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "") String searchQuery,
                            @RequestParam(defaultValue = "all") String statusFilter, // ✅ lọc trạng thái
                            Model model) {

        Page<Account> staffPage = staffService.getStaffPage(page, size, searchQuery, statusFilter);

        model.addAttribute("staffPage", staffPage);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("statusFilter", statusFilter);
        return "staff/staff-list";
    }

    // 🔹 Xem chi tiết nhân viên
    @GetMapping("/view/{id}")
    public String viewStaff(@PathVariable int id, Model model) {
        Account account = staffService.getById(id);
        model.addAttribute("account", account);
        return "staff/view-staff";
    }

    // 🔹 Form thêm nhân viên
    @GetMapping("/add")
    public String addStaffForm(Model model) {
        model.addAttribute("account", new Account());
        return "staff/add-staff";
    }

    // 🔹 Lưu nhân viên
    @PostMapping("/add")
    public String saveStaff(@ModelAttribute("account") Account account) {
        staffService.saveStaff(account);
        return "redirect:/staff/list?statusFilter=all";
    }

    // 🔹 Form sửa nhân viên
    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable int id, Model model) {
        model.addAttribute("account", staffService.getById(id));
        return "staff/edit-staff";
    }

    // 🔹 Cập nhật nhân viên
    @PostMapping("/edit")
    public String updateStaff(@ModelAttribute("account") Account account) {
        staffService.saveStaff(account);
        return "redirect:/staff/list?statusFilter=all";
    }

    // 🔹 Chuyển trạng thái (Active <-> Inactive)
    @GetMapping("/delete/{id}")
    public String deactivateStaff(@PathVariable int id) {
        staffService.deactivateStaff(id);
        return "redirect:/staff/list?statusFilter=all";
    }
}
