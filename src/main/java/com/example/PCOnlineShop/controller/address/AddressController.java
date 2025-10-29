package com.example.PCOnlineShop.controller.address;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // Quan trọng: Dùng @RestController để trả về JSON
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountRepository accountRepository;

    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    @PostMapping("/address/add")
    public ResponseEntity<?> addNewAddress(
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String address,
            @AuthenticationPrincipal UserDetails currentUserDetails) {

        Account account = getCurrentAccount(currentUserDetails);
        if (account == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Chưa đăng nhập"));
        }

        try {
            Address newAddress = addressService.addNewAddress(account, fullName, phone, address);
            return ResponseEntity.ok(newAddress); // Trả về địa chỉ mới (bao gồm cả ID)
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}