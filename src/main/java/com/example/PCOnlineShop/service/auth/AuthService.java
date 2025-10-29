package com.example.PCOnlineShop.service.auth;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    private final Map<String, String> resetCodeMap = new HashMap<>();


    public void register(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        if (accountRepository.existsByPhoneNumber(account.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(RoleName.Customer);
        account.setEnabled(true);
        accountRepository.save(account);
    }
    public void sendResetCode(String identifier) {
        // ✅ Tìm tài khoản theo email hoặc sđt
        Optional<Account> optionalAccount = accountRepository.findByEmail(identifier);
        if (optionalAccount.isEmpty()) {
            optionalAccount = accountRepository.findByPhoneNumber(identifier);
        }

        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản với thông tin đã nhập!");
        }

        // ✅ Tạo mã ngẫu nhiên 6 chữ số
        String code = String.format("%06d", new Random().nextInt(999999));
        resetCodeMap.put(identifier, code);

        // ✅ Gửi email
        Account account = optionalAccount.get();
        sendEmail(account.getEmail(), "Mã xác nhận đặt lại mật khẩu",
                "Xin chào " + account.getFullName() + ",\n\n" +
                        "Mã xác nhận của bạn là: " + code + "\n\n" +
                        "Mã này sẽ hết hạn sau 5 phút.\n\n" +
                        "Trân trọng,\nĐội ngũ PC Online Shop");
    }

    public boolean verifyResetCode(String identifier, String code) {
        String stored = resetCodeMap.get(identifier);
        return stored != null && stored.equals(code);
    }

    public void resetPassword(String identifier, String newPassword) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(identifier);
        if (optionalAccount.isEmpty()) {
            optionalAccount = accountRepository.findByPhoneNumber(identifier);
        }

        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản!");
        }

        Account acc = optionalAccount.get();
        acc.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(acc);

        // ✅ Xóa mã sau khi đổi mật khẩu xong
        resetCodeMap.remove(identifier);
    }

    // ⬇️ đổi kiểu trả về: Account
    public Account saveStaff(Account account) {

        accountRepository.findByEmail(account.getEmail()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
        });

        accountRepository.findByPhoneNumber(account.getPhoneNumber()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
            }
        });

        Account existing = accountRepository.findByIdentifier(String.valueOf(account.getAccountId())).orElse(null);

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

        account.setRole(RoleName.Staff);
        return accountRepository.save(account); // ⬅️ trả về để dùng lưu Address
    }

    public void saveCustomer(Account account) {
        accountRepository.findByEmail(account.getEmail()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
        });

        accountRepository.findByPhoneNumber(account.getPhoneNumber()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
            }
        });

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
    public void updatePassword(String email, String newPassword) {
        accountRepository.findByEmail(email).ifPresent(acc -> {
            acc.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(acc);
        });
    }

}
