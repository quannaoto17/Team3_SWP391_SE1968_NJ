package com.example.PCOnlineShop.service.staff;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final AccountRepository accountRepository;

    public StaffService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    // Lấy danh sách staff có phân trang
    public Page<Account> getStaffPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAllByRole(RoleName.Staff, pageable);
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
