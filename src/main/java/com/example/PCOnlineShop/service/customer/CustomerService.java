package com.example.PCOnlineShop.service.customer;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final AccountRepository accountRepository;

    public CustomerService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // 🔹 Lấy danh sách customer có thể tìm kiếm & lọc trạng thái
    public Page<Account> getCustomerPage(int page, int size, String searchQuery, String statusFilter) {
        Pageable pageable = PageRequest.of(page, size);

        boolean filterActive = "active".equalsIgnoreCase(statusFilter);
        boolean filterInactive = "inactive".equalsIgnoreCase(statusFilter);

        // ✅ Lọc theo trạng thái
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

    // 🔹 Lưu khách hàng
    public Account saveCustomer(Account account) {
        account.setRole(RoleName.Customer);
        return accountRepository.save(account);
    }

    // 🔹 Lấy theo ID
    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    // 🔹 Đổi trạng thái Active/Inactive
    public void deactivateCustomer(int id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setEnabled(!account.getEnabled());
            accountRepository.save(account);
        }
    }
}
