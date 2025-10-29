package com.example.PCOnlineShop.service.staff;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.account.AddressRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

// StaffService.java
@Service
public class StaffService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;

    public StaffService(AccountRepository accountRepository, AddressRepository addressRepository) {
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
    }

    public Page<Account> getStaffPage(int page, int size, String searchQuery, String statusFilter) {
        Pageable pageable = PageRequest.of(page, size);

        boolean filterActive = "active".equalsIgnoreCase(statusFilter);
        boolean filterInactive = "inactive".equalsIgnoreCase(statusFilter);

        if (filterActive) {
            return accountRepository.findByRoleAndEnabled(RoleName.Staff, true, searchQuery, pageable);
        } else if (filterInactive) {
            return accountRepository.findByRoleAndEnabled(RoleName.Staff, false, searchQuery, pageable);
        } else {
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                return accountRepository.findAllByRole(RoleName.Staff, pageable);
            } else {
                return accountRepository.findByPhoneNumberOrEmail(RoleName.Staff, searchQuery, pageable);
            }
        }
    }

    public Account saveStaff(Account account) {
        account.setRole(RoleName.Staff);
        return accountRepository.save(account);
    }

    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deactivateStaff(int id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setEnabled(!account.getEnabled());
            accountRepository.save(account);
        }
    }

    // ⬇️ NEW: Lưu địa chỉ mặc định khi Add
    public void saveDefaultAddress(Account account, String addressStr) {
        if (account == null) return;
        if (addressStr == null || addressStr.trim().isEmpty()) return;

        Address addr = new Address();
        addr.setAccount(account);
        addr.setFullName(account.getFullName());
        addr.setPhone(account.getPhoneNumber());
        addr.setAddress(addressStr.trim());
        addr.setDefault(true);

        // Optionally: bỏ default của các địa chỉ khác (nếu có)
        addressRepository.findByAccount(account).forEach(a -> {
            if (a.isDefault()) { a.setDefault(false); }
        });

        addressRepository.save(addr);
    }

    // ⬇️ NEW: Cập nhật địa chỉ mặc định khi Edit
    public void updateDefaultAddress(Account account, String addressStr) {
        if (account == null) return;
        if (addressStr == null || addressStr.trim().isEmpty()) return;

        Address defaultAddr = addressRepository.findDefaultByAccount(account).orElse(null);
        if (defaultAddr == null) {
            defaultAddr = new Address();
            defaultAddr.setAccount(account);
            defaultAddr.setDefault(true);
        }
        defaultAddr.setFullName(account.getFullName());
        defaultAddr.setPhone(account.getPhoneNumber());
        defaultAddr.setAddress(addressStr.trim());

        addressRepository.save(defaultAddr);
    }
}
