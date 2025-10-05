package com.example.PCOnlineShop.service.auth;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.Account;
import com.example.PCOnlineShop.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(Account account){
        // hash password before saving
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // set default role
        account.setRole(RoleName.ROLE_CUSTOMER);

        // set active to true
        account.setActive(true);

        // save to db
        accountRepository.save(account);
    }
}
