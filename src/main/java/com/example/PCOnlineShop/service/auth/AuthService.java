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

    // =========================================================
    // 🔹 Đăng ký khách hàng (Customer)
    // ➜ Giữ lại để không lỗi AuthController
    // =========================================================
    public void register(Account account) {
        // ✅ Kiểm tra email trùng
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        // ✅ Kiểm tra số điện thoại trùng
        if (accountRepository.existsByPhoneNumber(account.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }

        // Mã hóa và gán role Customer
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(RoleName.Customer);
        account.setEnabled(true);

        accountRepository.save(account);
    }

    // =========================================================
    // ✅ Thêm / Cập nhật Staff có kiểm tra trùng email & phone
    // =========================================================
    public void saveStaff(Account account) {

        // 🔹 Kiểm tra email trùng (với tài khoản khác)
        accountRepository.findByEmail(account.getEmail()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
        });

        // 🔹 Kiểm tra số điện thoại trùng
        accountRepository.findByPhoneNumber(account.getPhoneNumber()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
            }
        });

        // 🔹 Xác định là update hay thêm mới
        Account existing = accountRepository.findById(account.getAccountId()).orElse(null);

        if (existing != null) {
            // Nếu mật khẩu thay đổi thì mã hóa lại
            if (!existing.getPassword().equals(account.getPassword())) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                account.setPassword(existing.getPassword());
            }
            // Giữ trạng thái enabled cũ
            account.setEnabled(existing.getEnabled());
        } else {
            // Nếu là thêm mới
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEnabled(true);
        }

        account.setRole(RoleName.Staff);
        accountRepository.save(account);
    }

    // =========================================================
    // ✅ Thêm / Cập nhật Customer có kiểm tra trùng email & phone
    // =========================================================
    public void saveCustomer(Account account) {

        // 🔹 Kiểm tra email trùng (với tài khoản khác)
        accountRepository.findByEmail(account.getEmail()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
        });

        // 🔹 Kiểm tra số điện thoại trùng
        accountRepository.findByPhoneNumber(account.getPhoneNumber()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
            }
        });

        // 🔹 Xác định là update hay thêm mới
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
