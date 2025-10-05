package com.example.PCOnlineShop.service.customer;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final AccountRepository accountRepository;

    public CustomerService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Lấy danh sách Customer có phân trang
    public Page<Account> getCustomerPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAllByRole(RoleName.Customer, pageable);
    }

    public Account saveCustomer(Account account) {
        account.setRole(RoleName.Customer);
        return accountRepository.save(account);
    }

    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deactivateCustomer(int id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setEnabled(!account.getEnabled()); // toggle trạng thái
            accountRepository.save(account);
        }
    }
}
