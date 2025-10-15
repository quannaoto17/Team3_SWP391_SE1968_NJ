package com.example.PCOnlineShop.repository.account;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    // 🔹 Lấy toàn bộ theo role (VD: Staff, Customer, Admin)
    Page<Account> findAllByRole(RoleName role, Pageable pageable);

    // 🔹 Tìm theo số điện thoại (đăng nhập hoặc kiểm tra trùng)
    Optional<Account> findByPhoneNumber(String phoneNumber);

    // 🔹 ✅ Tìm theo email (để kiểm tra trùng)
    Optional<Account> findByEmail(String email);

    // 🔹 Tìm theo role + tìm kiếm phone/email
    @Query("""
        SELECT a FROM Account a 
        WHERE a.role = :role 
          AND (a.phoneNumber LIKE %:searchQuery% OR a.email LIKE %:searchQuery%)
    """)
    Page<Account> findByPhoneNumberOrEmail(@Param("role") RoleName role,
                                           @Param("searchQuery") String searchQuery,
                                           Pageable pageable);

    // 🔹 ✅ Tìm theo role + trạng thái (Active / Inactive) + tìm kiếm
    @Query("""
        SELECT a FROM Account a 
        WHERE a.role = :role
          AND a.enabled = :enabled
          AND (:searchQuery = '' OR a.phoneNumber LIKE %:searchQuery% OR a.email LIKE %:searchQuery%)
    """)
    Page<Account> findByRoleAndEnabled(@Param("role") RoleName role,
                                       @Param("enabled") Boolean enabled,
                                       @Param("searchQuery") String searchQuery,
                                       Pageable pageable);

    boolean existsByPhoneNumberAndRole(String phoneNumber, RoleName role);
}
