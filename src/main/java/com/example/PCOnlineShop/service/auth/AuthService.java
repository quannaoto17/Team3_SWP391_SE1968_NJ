package com.example.PCOnlineShop.service.auth;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
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

    // Lưu mã reset tạm thời (theo email/phone)
    private final Map<String, String> resetCodeMap = new HashMap<>();

    /* ===================== AUTH (CUSTOMER) ===================== */

    public void register(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        if (accountRepository.existsByPhoneNumber(account.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(RoleName.Customer);
        account.setEnabled(false);
        accountRepository.save(account);
    }
    public Account getByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }


    @Async
    public void sendResetCode(String identifier) {
        // Tìm theo email trước, không có thì theo phone
        Optional<Account> optionalAccount = accountRepository.findByEmail(identifier);
        if (optionalAccount.isEmpty()) {
            optionalAccount = accountRepository.findByPhoneNumber(identifier);
        }
        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản với thông tin đã nhập!");
        }

        // Tạo mã 6 chữ số
        String code = String.format("%06d", new Random().nextInt(999999));
        resetCodeMap.put(identifier, code);

        Account acc = optionalAccount.get();
        String content =
                "Xin chào " + acc.getFullName() + ",\n\n" +
                        "Mã xác nhận của bạn là: " + code + "\n\n" +
                        "Mã này sẽ hết hạn sau 5 phút.\n\n" +
                        "Trân trọng,\nPC Online Shop";
        sendEmail(acc.getEmail(), "Mã xác nhận đặt lại mật khẩu", content);
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

        // Xoá mã sau khi dùng
        resetCodeMap.remove(identifier);
    }
    public boolean changePassword(String phoneNumber, String currentPassword, String newPassword) {
        Optional<Account> optionalAccount = accountRepository.findByPhoneNumber(phoneNumber);
        if (optionalAccount.isEmpty()) {
            return false; // không tìm thấy user
        }

        Account account = optionalAccount.get();

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
            return false; // mật khẩu hiện tại không đúng
        }

        // Encode mật khẩu mới và lưu
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);
        return true;
    }


    /* ===================== MAIL HELPER ===================== */

    private void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Thêm dòng này để xem lỗi thật
            throw new IllegalArgumentException("Không thể gửi email! " + e.getMessage());
        }
    }


    /* ===================== STAFF / CUSTOMER MGMT ===================== */

    /** Dùng cho tạo/cập nhật Staff. Trả về Account để client (DataTable/Ajax) dùng. */
    public Account saveStaff(Account account) {
        // Validate unique email
        accountRepository.findByEmail(account.getEmail()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
        });

        // Validate unique phone
        accountRepository.findByPhoneNumber(account.getPhoneNumber()).ifPresent(existing -> {
            if (account.getAccountId() == 0 || existing.getAccountId() != account.getAccountId()) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
            }
        });

        // Nếu update: giữ enabled, giữ password cũ nếu không đổi
        Account existing = (account.getAccountId() == 0)
                ? null
                : accountRepository.findById(account.getAccountId()).orElse(null);

        if (existing != null) {
            // Đổi mật khẩu nếu khác chuỗi hiện tại
            if (!existing.getPassword().equals(account.getPassword())) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                account.setPassword(existing.getPassword());
            }
            account.setEnabled(existing.getEnabled());
        } else {
            // Tạo mới
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setEnabled(true);
        }

        account.setRole(RoleName.Staff);
        return accountRepository.save(account);
    }

    /** Dùng cho tạo/cập nhật Customer. */
    public Account saveCustomer(Account account) {
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

        Account existing = (account.getAccountId() == 0)
                ? null
                : accountRepository.findById(account.getAccountId()).orElse(null);

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
        return accountRepository.save(account);
    }


    public void updatePassword(String email, String newPassword) {
        accountRepository.findByEmail(email).ifPresent(acc -> {
            acc.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(acc);
        });
    }

    @Async
    public void sendVerifyCode(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản với email đã nhập!");
        }

        String code = String.format("%06d", new Random().nextInt(999999));
        resetCodeMap.put(email, code);

        Account acc = optionalAccount.get();
        String content =
                "Xin chào " + acc.getFullName() + ",\n\n" +
                        "Cảm ơn bạn đã đăng ký tài khoản tại PC Online Shop.\n\n" +
                        "Mã xác thực tài khoản của bạn là: " + code + "\n\n" +
                        "Vui lòng nhập mã này trong vòng 5 phút để kích hoạt tài khoản.\n\n" +
                        "Trân trọng,\nPC Online Shop";

        sendEmail(acc.getEmail(), "Xác thực tài khoản PC Online Shop", content);
    }

    public void verifyAccount(String email, String code) {
        String storedCode = resetCodeMap.get(email);
        if (storedCode == null || !storedCode.equals(code)) {
            throw new IllegalArgumentException("Mã xác thực không hợp lệ hoặc đã hết hạn!");
        }

        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản!");
        }

        Account acc = optionalAccount.get();
        acc.setEnabled(true);
        accountRepository.save(acc);

        // Xóa mã sau khi xác thực thành công
        resetCodeMap.remove(email);
    }

}
