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

        List<Account> customerList = customerService.getAllCustomers(statusFilter);

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
        model.addAttribute("account", new Account());
        return "customer/add-customer";
    }

    @PostMapping("/add")
    public String saveCustomer(@Valid @ModelAttribute("account") Account account,
                               BindingResult result,
                               @RequestParam(name = "addressStr", required = false) String addressStr,
                               Model model) {
        if (result.hasErrors()) return "customer/add-customer";

        try {
            Account saved = authService.saveCustomer(account);
            customerService.saveDefaultAddress(saved, addressStr);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/add-customer";
        }

        return "redirect:/customer/list";
    }

    // ======================== EDIT ========================
    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable int id, Model model) {
        model.addAttribute("account", customerService.getById(id));
        return "customer/edit-customer";
    }

    @PostMapping("/edit")
    public String updateCustomer(@Valid @ModelAttribute("account") Account account,
                                 BindingResult result,
                                 @RequestParam(name = "addressStr", required = false) String addressStr,
                                 Model model) {
        if (result.hasErrors()) return "customer/edit-customer";

        try {
            Account saved = authService.saveCustomer(account);
            customerService.updateDefaultAddress(saved, addressStr);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/edit-customer";
        }

        return "redirect:/customer/list";
    }

    // ======================== DELETE ========================
    @GetMapping("/delete/{id}")
    public String deactivateCustomer(@PathVariable int id) {
        customerService.deactivateCustomer(id);
        return "redirect:/customer/list";
    }
}
