package com.example.PCOnlineShop.service.customer;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final AccountRepository accountRepository;

    public CustomerService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Lấy danh sách khách hàng với tìm kiếm và sắp xếp
    public Page<Account> getCustomerPage(int page, int size, String searchQuery, String sortOrder) {
        Pageable pageable = getSortedPageable(page, size, sortOrder.toLowerCase()); // Ensure case-insensitive handling
        return searchCustomers(searchQuery, pageable);
    }

    // Tìm kiếm khách hàng theo phone hoặc email
    private Page<Account> searchCustomers(String searchQuery, Pageable pageable) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return accountRepository.findAllByRole(RoleName.Customer, pageable);
        } else {
            return accountRepository.findByPhoneNumberOrEmail(RoleName.Customer, searchQuery, pageable);
        }
    }

    // Tạo Pageable với sắp xếp theo accountId
    private Pageable getSortedPageable(int page, int size, String sortOrder) {
        Sort sort = Sort.by("accountId");
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        return PageRequest.of(page, size, sort);
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