package com.example.PCOnlineShop.repository.customer;

import com.example.PCOnlineShop.model.customer.Customer ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
