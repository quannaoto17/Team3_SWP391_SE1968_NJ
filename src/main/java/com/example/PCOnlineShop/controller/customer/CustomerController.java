package com.example.PCOnlineShop.controller.customer;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.service.auth.AuthService;
import com.example.PCOnlineShop.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;

    @GetMapping("/list")
    public String listCustomers(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "active") String statusFilter,
                                Model model) {

        int safePage = Math.max(page, 1);
        int zeroBasedPage = safePage - 1;

        Page<Account> customerPage =
                customerService.getCustomerPage(zeroBasedPage, size, statusFilter);

        model.addAttribute("customerPage", customerPage);
        model.addAttribute("statusFilter", statusFilter);
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "customer/customer-list";
    }

    @GetMapping("/view/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {
        model.addAttribute("account", customerService.getById(id));
        return "customer/view-customer";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("account", new Account());
        return "customer/add-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @ModelAttribute("account") Account account,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "customer/add-customer";
        }

        try {
            authService.saveCustomer(account);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/add-customer";
        }

        return "redirect:/customer/list?statusFilter=all";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable int id, Model model) {
        model.addAttribute("account", customerService.getById(id));
        return "customer/edit-customer";
    }

    @PostMapping("/edit")
    public String updateCustomer(@Valid @ModelAttribute("account") Account account,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            return "customer/edit-customer";
        }

        try {
            authService.saveCustomer(account);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/edit-customer";
        }

        return "redirect:/customer/list?statusFilter=all";
    }

    @GetMapping("/delete/{id}")
    public String deactivateCustomer(@PathVariable int id) {
        customerService.deactivateCustomer(id);
        return "redirect:/customer/list?statusFilter=all";
    }
}
