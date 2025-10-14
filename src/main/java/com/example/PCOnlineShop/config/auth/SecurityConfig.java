package com.example.PCOnlineShop.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // disable csrf
                .csrf(AbstractHttpConfigurer::disable)

                // filter
                .authorizeHttpRequests(auth -> auth
                        // Public routes - không cần đăng nhập
                        .requestMatchers("/", "/home", "/auth/**").permitAll()
                        .requestMatchers("/assets/**", "/css/**", "/js/**", "/image/**", "/static/**").permitAll()

                        // ADMIN + STAFF - quản lý nhân viên, sản phẩm, bảo hành
                        .requestMatchers("/staff/list/**", "/staff/add/**", "/staff/edit/**", "/staff/view/**", "/staff/delete/**").hasAnyRole("ADMIN")
                        .requestMatchers("/staff/products/**").hasAnyRole( "STAFF")
                        .requestMatchers("/staff/warranty/**").hasAnyRole( "STAFF")

                        // ADMIN + STAFF - quản lý TẤT CẢ đơn hàng trong hệ thống
                        .requestMatchers("/staff/orders/**").hasAnyRole( "STAFF")

                        // CUSTOMER - xem đơn hàng của CHÍNH MÌNH
                        .requestMatchers("/customer/orders/**").hasRole("CUSTOMER")

                        // ADMIN + STAFF - quản lý thông tin khách hàng (không bao gồm orders)
                        .requestMatchers("/customer/list/**", "/customer/add/**", "/customer/edit/**", "/customer/view/**", "/customer/delete/**").hasAnyRole("ADMIN", "STAFF")

                        // Authenticated users - build PC
                        .requestMatchers("/build/**").authenticated()

                        // Tất cả các route khác cần đăng nhập
                        .anyRequest().authenticated()
                )

                // form login
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("phoneNumber")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", false)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )

                // logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
