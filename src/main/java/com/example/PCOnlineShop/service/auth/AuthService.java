package com.example.PCOnlineShop.service.auth;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(Account account) {
        // hash password before saving
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        //set default role
        account.setRole(RoleName.Customer);

        //set enable to true
        account.setEnabled(true);

        // save to db
        accountRepository.save(account);
    }

    public void saveStaff(Account account) {
        // Tìm trong DB xem staff này đã có chưa
        Account existing = accountRepository.findById(account.getAccountId()).orElse(null);

        // ✅ Nếu có trong DB
        if (existing != null) {
            // Nếu mật khẩu mới khác mật khẩu cũ (tức là người dùng đã đổi mật khẩu)
            if (!existing.getPassword().equals(account.getPassword())) {
                account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa lại
            } else {
                account.setPassword(existing.getPassword()); // giữ nguyên hash cũ
            }

            // Giữ nguyên trạng thái enable
            account.setEnabled(existing.getEnabled());
        } else {
            // ✅ Nếu là thêm mới → mã hóa luôn
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEnabled(true);
        }

        // Set role đúng
        account.setRole(RoleName.Staff);

        // Lưu lại
        accountRepository.save(account);
    }


    public void saveCustomer(Account account) {
        // 🔹 Tìm trong DB xem Customer này đã tồn tại chưa
        Account existing = accountRepository.findById(account.getAccountId()).orElse(null);

        if (existing != null) {
            // 🔹 Nếu mật khẩu thay đổi → mã hóa lại
            if (!existing.getPassword().equals(account.getPassword())) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                // 🔹 Nếu mật khẩu không đổi → giữ nguyên hash cũ
                account.setPassword(existing.getPassword());
            }

            // 🔹 Giữ nguyên trạng thái enabled
            account.setEnabled(existing.getEnabled());
        } else {
            // 🔹 Nếu là thêm mới → mã hóa bình thường
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEnabled(true);
        }

        // 🔹 Set role Customer
        account.setRole(RoleName.Customer);

        // 🔹 Lưu vào DB
        accountRepository.save(account);
    }


}
