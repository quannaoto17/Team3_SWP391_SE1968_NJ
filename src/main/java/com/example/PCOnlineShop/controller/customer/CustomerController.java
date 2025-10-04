package com.example.PCOnlineShop.controller.customer;

import com.example.PCOnlineShop.model.customer.Customer;
import com.example.PCOnlineShop.service.customer.CustomerService;
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
    public String listCustomers(Model model) {
        model.addAttribute("customerList", customerService.getAllCustomers());
        return "customer/customer-list";
    }

    // Form thêm khách hàng
    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/add-customer";
    }

    // Lưu khách hàng
    @PostMapping("/add")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/list";
    }

    // Form sửa khách hàng
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer/edit-customer";
    }

    // Cập nhật khách hàng
    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/list";
    }

    // Bật/tắt hoạt động
    @GetMapping("/toggle/{id}")
    public String toggleCustomerStatus(@PathVariable int id) {
        customerService.deactivateCustomer(id);
        return "redirect:/customer/list";
    }
}
