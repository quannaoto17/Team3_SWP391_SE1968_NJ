package com.example.PCOnlineShop.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

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
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/auth/**","/build/**").permitAll()
                        .requestMatchers("/assets/**", "/css/**", "/js/**", "/image/**", "/static/**").permitAll()
                        .requestMatchers("/staff/list/**", "/staff/add/**", "/staff/edit/**", "/staff/view/**", "/staff/delete/**").hasAnyRole("ADMIN")
                        .requestMatchers("/staff/products/**", "/staff/warranty/**",  "/staff/shipping/**").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/orders/list", "/orders/detail/**").hasAnyRole("CUSTOMER", "STAFF", "ADMIN")
                        .requestMatchers("/cart/**").hasRole("CUSTOMER") // Only Customers can access the cart
                        .requestMatchers("/checkout/**", "/payment/**").hasRole("CUSTOMER")

                        // --- Đường dẫn cập nhật chỉ cho Staff/Admin ---
                        .requestMatchers("/orders/update-all-status").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/customer/list/**", "/customer/add/**", "/customer/edit/**", "/customer/view/**", "/customer/delete/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/staff/feedback/**").hasAnyRole("STAFF")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("phoneNumber")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", false)
                        .failureUrl("/auth/login?error=true")
                        .successHandler((request, response, authentication) -> {

                            var successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
                            successHandler.setDefaultTargetUrl("/home");

                            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STAFF"))) {
                                response.sendRedirect("/dashboard/staff");
                            } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                                response.sendRedirect("/dashboard/admin");
                            } else{
                                successHandler.onAuthenticationSuccess(request, response, authentication);
                            }
                        })
                        .permitAll()
                )
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
