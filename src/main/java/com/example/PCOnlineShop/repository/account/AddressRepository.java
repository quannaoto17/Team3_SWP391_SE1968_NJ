package com.example.PCOnlineShop.repository.account;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByAccount(Account account);

    @Query("SELECT a FROM Address a WHERE a.account = :account AND a.isDefault = true")
    Optional<Address> findDefaultByAccount(Account account);
}