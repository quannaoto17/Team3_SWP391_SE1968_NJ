package com.example.PCOnlineShop.config.auth;

import com.example.PCOnlineShop.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUserDetails implements UserDetails {

    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().name().toUpperCase()));
    }


    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getPhoneNumber();
    }

    // Các method còn lại của UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true; // hoặc tùy bạn muốn kiểm soát ngày hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // hoặc thêm cột "locked" trong DB nếu cần
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getEnabled(); // lấy từ DB
    }
}
