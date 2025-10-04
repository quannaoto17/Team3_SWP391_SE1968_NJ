package com.example.PCOnlineShop.service.customer;

import com.example.PCOnlineShop.model.customer.Customer;
import com.example.PCOnlineShop.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void deactivateCustomer(int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setEnabled(!customer.getEnabled()); // toggle trạng thái
            customerRepository.save(customer);
        }
    }
}
