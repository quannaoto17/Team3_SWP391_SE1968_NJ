package com.example.PCOnlineShop.controller.customer;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import com.example.PCOnlineShop.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;

    // ======================== LIST ========================
    @GetMapping("/list")
    public String listCustomers(@RequestParam(defaultValue = "active") String statusFilter,
                                Model model) {
        // Lấy toàn bộ danh sách khách hàng theo trạng thái (active/inactive)
        List<Account> customerList = customerService.getAllCustomers(statusFilter);
        // Gửi dữ liệu sang view
        model.addAttribute("customerList", customerList);
        model.addAttribute("statusFilter", statusFilter);

        return "customer/customer-list";
    }

    // ======================== VIEW ========================
    @GetMapping("/view/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {
        model.addAttribute("account", customerService.getById(id));
        return "customer/view-customer";
    }

    // ======================== ADD ========================
    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        // Tạo object Account rỗng để binding vào form
        model.addAttribute("account", new Account());
        return "customer/add-customer";
    }

    @PostMapping("/add")
    public String saveCustomer(@Valid @ModelAttribute("account") Account account,
                               BindingResult result,
                               @RequestParam(name = "addressStr", required = false) String addressStr,
                               Model model) {
        // Nếu lỗi validation → quay lại form add
        if (result.hasErrors()) return "customer/add-customer";

        try {
            // Lưu thông tin tài khoản khách hàng
            Account saved = authService.saveCustomer(account);
            // Lưu địa chỉ mặc định
            customerService.saveDefaultAddress(saved, addressStr);
        } catch (IllegalArgumentException e) {
            // Bắt lỗi duplicate email, phone,...
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/add-customer";
        }

        return "redirect:/customer/list";
    }
}
