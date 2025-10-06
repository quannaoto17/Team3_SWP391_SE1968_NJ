package com.example.PCOnlineShop.repository.account;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findAllByRole(RoleName role, Pageable pageable);
    Optional<Account> findByPhoneNumber(String phoneNumber);

    @Query("SELECT a FROM Account a WHERE a.role = :role AND (a.phoneNumber LIKE %:searchQuery% OR a.email LIKE %:searchQuery%)")
    Page<Account> findByPhoneNumberOrEmail(@Param("role") RoleName role, @Param("searchQuery") String searchQuery, Pageable pageable);
}