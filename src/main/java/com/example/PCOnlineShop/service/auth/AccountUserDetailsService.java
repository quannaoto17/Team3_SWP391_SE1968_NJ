package com.example.PCOnlineShop.service.auth;

import com.example.PCOnlineShop.config.auth.AccountUserDetails;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

  private final AccountRepository accountRepository;
   @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Account account = accountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
        return new AccountUserDetails(account);
    }

}
