package com.example.PCOnlineShop.service.customer;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.account.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;

    // ✅ Lấy ALL customer (client-side paging bằng DataTables)
    public List<Account> getAllCustomers(String statusFilter) {

        if ("active".equalsIgnoreCase(statusFilter)) {
            return accountRepository.findByRoleAndEnabledWithAddresses(RoleName.Customer, true);
        }

        if ("inactive".equalsIgnoreCase(statusFilter)) {
            return accountRepository.findByRoleAndEnabledWithAddresses(RoleName.Customer, false);
        }

        // ALL
        return accountRepository.findAllByRoleWithAddresses(RoleName.Customer);
    }

    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    // Lưu địa chỉ mặc định khi tạo customer
    public void saveDefaultAddress(Account account, String addressStr) {
        if (account == null || addressStr == null || addressStr.trim().isEmpty()) return;

        // Hạ default cũ
        addressRepository.findByAccount(account).forEach(a -> a.setDefault(false));

        Address addr = new Address();
        addr.setAccount(account);
        addr.setFullName(account.getFullName());
        addr.setPhone(account.getPhoneNumber());
        addr.setAddress(addressStr.trim());
        addr.setDefault(true);

        addressRepository.save(addr);
    }

}
