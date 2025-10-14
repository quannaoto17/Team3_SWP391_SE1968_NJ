package com.example.PCOnlineShop.service.auth;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    // 🔹 Đăng ký khách hàng (Customer)
    public void register(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(RoleName.Customer);
        account.setEnabled(true);
        accountRepository.save(account);
    }

    // ✅ Thêm / Cập nhật Staff có kiểm tra trùng email & phone
    public void saveStaff(Account account) {
        // 🔹 Kiểm tra email trùng
        Optional<Account> emailExists = accountRepository.findByEmail(account.getEmail());
        if (emailExists.isPresent() && emailExists.get().getAccountId() != account.getAccountId()) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        // 🔹 Kiểm tra số điện thoại trùng
        Optional<Account> phoneExists = accountRepository.findByPhoneNumber(account.getPhoneNumber());
        if (phoneExists.isPresent() && phoneExists.get().getAccountId() != account.getAccountId()) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }

        // 🔹 Tìm xem đang update hay thêm mới
        Account existing = accountRepository.findById(account.getAccountId()).orElse(null);

        if (existing != null) {
            // Nếu mật khẩu thay đổi thì mã hóa lại
            if (!existing.getPassword().equals(account.getPassword())) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                account.setPassword(existing.getPassword());
            }

            // Giữ nguyên trạng thái enabled
            account.setEnabled(existing.getEnabled());
        } else {
            // Nếu là thêm mới
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEnabled(true);
        }

        // ✅ Set role staff rồi lưu
        account.setRole(RoleName.Staff);
        accountRepository.save(account);
    }

    // 🔹 Cập nhật / thêm Customer (nếu cần)
    public void saveCustomer(Account account) {
        Account existing = accountRepository.findById(account.getAccountId()).orElse(null);

        if (existing != null) {
            if (!existing.getPassword().equals(account.getPassword())) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                account.setPassword(existing.getPassword());
            }
            account.setEnabled(existing.getEnabled());
        } else {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEnabled(true);
        }

        account.setRole(RoleName.Customer);
        accountRepository.save(account);
    }
}
