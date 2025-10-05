package com.example.PCOnlineShop.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Dùng BCrypt để mã hóa mật khẩu
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // tắt CSRF để test form
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // cho phép truy cập tất cả endpoint
                )
                .formLogin(form -> form.disable()) // tắt form login
                .httpBasic(httpBasic -> httpBasic.disable()); // tắt Basic Auth
        return http.build();
    }

}