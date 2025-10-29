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

    /**
     * Lấy danh sách customer có phân trang + lọc trạng thái
     * KHÔNG cần search theo tên/sđt/email ở đây nữa
     * vì DataTables đã xử lý search client-side
     */
    public Page<Account> getCustomerPage(int page, int size, String statusFilter) {
        Pageable pageable = PageRequest.of(page, size);

        if ("active".equalsIgnoreCase(statusFilter)) {
            return accountRepository.findByRoleAndEnabled(RoleName.Customer, true, pageable);
        }
        if ("inactive".equalsIgnoreCase(statusFilter)) {
            return accountRepository.findByRoleAndEnabled(RoleName.Customer, false, pageable);
        }
        // all
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
        accountRepository.findById(id).ifPresent(acc -> {
            acc.setEnabled(!acc.getEnabled());
            accountRepository.save(acc);
        });
    }
}
