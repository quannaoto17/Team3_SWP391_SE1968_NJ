package com.example.PCOnlineShop.service.staff;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    private final AccountRepository accountRepository;

    public StaffService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // 🔹 Lấy danh sách staff có thể tìm kiếm & lọc trạng thái
    public Page<Account> getStaffPage(int page, int size, String searchQuery, String statusFilter) {
        Pageable pageable = PageRequest.of(page, size);

        boolean filterActive = "active".equalsIgnoreCase(statusFilter);
        boolean filterInactive = "inactive".equalsIgnoreCase(statusFilter);

        // ✅ Lọc theo trạng thái
        if (filterActive) {
            return accountRepository.findByRoleAndEnabled(RoleName.Staff, true, searchQuery, pageable);
        } else if (filterInactive) {
            return accountRepository.findByRoleAndEnabled(RoleName.Staff, false, searchQuery, pageable);
        } else {
            // all
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                return accountRepository.findAllByRole(RoleName.Staff, pageable);
            } else {
                return accountRepository.findByPhoneNumberOrEmail(RoleName.Staff, searchQuery, pageable);
            }
        }
    }

    // 🔹 Lưu nhân viên
    public Account saveStaff(Account account) {
        account.setRole(RoleName.Staff);
        return accountRepository.save(account);
    }

    // 🔹 Lấy theo ID
    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    // 🔹 Đổi trạng thái Active/Inactive
    public void deactivateStaff(int id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setEnabled(!account.getEnabled());
            accountRepository.save(account);
        }
    }
}
