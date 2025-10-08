package com.example.PCOnlineShop.controller.customer;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.customer.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Danh sách khách hàng
    @GetMapping("/list")
    public String listCustomers(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "") String searchQuery,
                                @RequestParam(defaultValue = "asc") String sortOrder,
                                Model model) {
        Page<Account> customerPage = customerService.getCustomerPage(page, size, searchQuery, sortOrder);
        model.addAttribute("customerPage", customerPage);
        return "customer/customer-list";
    }

    // Hiển thị chi tiết khách hàng
    @GetMapping("/view/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {
        Account account = customerService.getById(id);
        model.addAttribute("account", account);
        return "customer/view-customer";
    }


    // Form thêm khách hàng
    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("account", new Account());
        return "customer/add-customer";
    }

    // Lưu khách hàng
    @PostMapping("/add")
    public String saveCustomer(@ModelAttribute("account") Account account) {
        customerService.saveCustomer(account);
        return "redirect:/customer/list?sortOrder=" + (account.getAccountId() > 0 ? "asc" : "asc"); // Default to asc after save
    }

    // Form edit
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable int id, Model model) {
        model.addAttribute("account", customerService.getById(id));
        return "customer/edit-customer";
    }

    // Update khách hàng
    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute("account") Account account) {
        customerService.saveCustomer(account);
        return "redirect:/customer/list?sortOrder=" + (account.getAccountId() > 0 ? "asc" : "asc"); // Default to asc after update
    }

    // Chuyển khách hàng sang Inactive thay vì xóa
    @GetMapping("/delete/{id}")
    public String deactivateCustomer(@PathVariable int id) {
        customerService.deactivateCustomer(id);
        return "redirect:/customer/list?sortOrder=asc"; // Default to asc after delete
    }
}