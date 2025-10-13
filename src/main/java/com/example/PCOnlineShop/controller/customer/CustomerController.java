package com.example.PCOnlineShop.controller.customer;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import com.example.PCOnlineShop.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;

    // 🔹 Danh sách khách hàng (lọc theo trạng thái)
    @GetMapping("/list")
    public String listCustomers(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "") String searchQuery,
                                @RequestParam(defaultValue = "all") String statusFilter,
                                Model model) {

        Page<Account> customerPage = customerService.getCustomerPage(Math.max(page, 0), size, searchQuery, statusFilter);

        model.addAttribute("customerPage", customerPage);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("statusFilter", statusFilter);
        return "customer/customer-list";
    }

    // 🔹 Xem chi tiết khách hàng
    @GetMapping("/view/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {
        Account account = customerService.getById(id);
        model.addAttribute("account", account);
        return "customer/view-customer";
    }

    // 🔹 Form thêm khách hàng
    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("account", new Account());
        return "customer/add-customer";
    }

    // 🔹 Lưu khách hàng
    @PostMapping("/add")
    public String addCustomer(@ModelAttribute("account") Account account) {
        authService.saveCustomer(account);
        return "redirect:/customer/list?statusFilter=all";
    }

    // 🔹 Form sửa
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable int id, Model model) {
        model.addAttribute("account", customerService.getById(id));
        return "customer/edit-customer";
    }

    // 🔹 Cập nhật khách hàng
    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute("account") Account account) {
        authService.saveCustomer(account);
        return "redirect:/customer/list?statusFilter=all";
    }

    // 🔹 Chuyển trạng thái (Active <-> Inactive)
    @GetMapping("/delete/{id}")
    public String deactivateCustomer(@PathVariable int id) {
        customerService.deactivateCustomer(id);
        return "redirect:/customer/list?statusFilter=all";
    }
}
