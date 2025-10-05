package com.example.PCOnlineShop.repository.account;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findAllByRole(RoleName role, Pageable pageable);
    Optional<Account> findByPhoneNumber(String phoneNumber);
}

