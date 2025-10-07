package com.example.PCOnlineShop.repository.order;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAccount(Account account);
}
