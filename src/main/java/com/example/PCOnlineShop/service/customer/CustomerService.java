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

    // 🔹 Lấy danh sách khách hàng có thể tìm kiếm & lọc trạng thái
    public Page<Account> getCustomerPage(int page, int size, String searchQuery, String statusFilter) {
        Pageable pageable = PageRequest.of(page, size);

        boolean filterActive = "active".equalsIgnoreCase(statusFilter);
        boolean filterInactive = "inactive".equalsIgnoreCase(statusFilter);

        if (filterActive) {
            return accountRepository.findByRoleAndEnabled(RoleName.Customer, true, searchQuery, pageable);
        } else if (filterInactive) {
            return accountRepository.findByRoleAndEnabled(RoleName.Customer, false, searchQuery, pageable);
        } else {
            // all
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                return accountRepository.findAllByRole(RoleName.Customer, pageable);
            } else {
                return accountRepository.findByPhoneNumberOrEmail(RoleName.Customer, searchQuery, pageable);
            }
        }
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
            account.setEnabled(!account.getEnabled());
            accountRepository.save(account);
        }
    }
}
