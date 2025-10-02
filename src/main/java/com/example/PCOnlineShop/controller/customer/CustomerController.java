package com.example.PCOnlineShop.controller.customer;

import com.example.PCOnlineShop.model.customer.Customer;
import com.example.PCOnlineShop.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository repo;

    @GetMapping({"", "/"})
    public String showCustomerList(Model model) {
        List<Customer> customers = repo.findAll();
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }

}
