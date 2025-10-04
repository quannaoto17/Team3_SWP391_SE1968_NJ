package com.example.PCOnlineShop.service.staff;

import com.example.PCOnlineShop.model.staff.Account;
import com.example.PCOnlineShop.repository.staff.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final AccountRepository accountRepository;

    public StaffService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllStaff() {
        return accountRepository.findAll();
    }

    public Account saveStaff(Account account) {
        return accountRepository.save(account);
    }

    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deactivateStaff(int id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setEnabled(false); // chuyển sang Inactive
            accountRepository.save(account);
        }
    }
}


