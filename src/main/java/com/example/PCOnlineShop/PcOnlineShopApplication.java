package com.example.PCOnlineShop;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PcOnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcOnlineShopApplication.class, args);
    }

    @Bean
    CommandLineRunner initAccounts(AccountRepository accountRepository) {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // ================= ADMIN =================
            String adminPhone = "0999999999";
            if (accountRepository.findByPhoneNumber(adminPhone).isEmpty()) {
                Account admin = new Account();
                admin.setFirstname("System");
                admin.setLastname("Admin");
                admin.setPhoneNumber(adminPhone);
                admin.setEmail("admin@shop.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(RoleName.Admin);
                admin.setGender(true);
                admin.setAddress("Hà Nội, Việt Nam");
                admin.setEnabled(true);
                accountRepository.save(admin);
                System.out.println("✅ Admin account created: admin@shop.com / admin123");
            }

            // ================= STAFF =================
            String staffPhone = "0888888888";
            if (accountRepository.findByPhoneNumber(staffPhone).isEmpty()) {
                Account staff = new Account();
                staff.setFirstname("quan");
                staff.setLastname("staff");
                staff.setPhoneNumber(staffPhone);
                staff.setEmail("staff@shop.com");
                staff.setPassword(passwordEncoder.encode("staff123"));
                staff.setRole(RoleName.Staff);
                staff.setGender(true);
                staff.setAddress("TP. Hồ Chí Minh, Việt Nam");
                staff.setEnabled(true);
                accountRepository.save(staff);
                System.out.println("✅ Staff account created: staff@shop.com / staff123");
            }

            // ================= CUSTOMER =================
            String customerPhone = "0777777777";
            if (accountRepository.findByPhoneNumber(customerPhone).isEmpty()) {
                Account customer = new Account();
                customer.setFirstname("quan");
                customer.setLastname("Customer");
                customer.setPhoneNumber(customerPhone);
                customer.setEmail("customer@shop.com");
                customer.setPassword(passwordEncoder.encode("cus123"));
                customer.setRole(RoleName.Customer);
                customer.setGender(false);
                customer.setAddress("Đà Nẵng, Việt Nam");
                customer.setEnabled(true);
                accountRepository.save(customer);
                System.out.println("✅ Customer account created: customer@shop.com / cus123");
            }

            System.out.println("✅ Default accounts initialized successfully!");
        };
    }
}
