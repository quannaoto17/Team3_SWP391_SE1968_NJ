package com.example.PCOnlineShop.repository.staff;

import com.example.PCOnlineShop.model.staff.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
