package com.example.PCOnlineShop.repository.account;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    /* ==================== Basic lookup ==================== */
    Optional<Account> findByPhoneNumber(String phoneNumber);
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    /* ==================== Dùng cho DataTable (trả ALL) ==================== */
    long countByRole(RoleName role);

    // Fetch luôn địa chỉ mặc định (JOIN FETCH)
    @Query("""
        SELECT DISTINCT a FROM Account a 
        LEFT JOIN FETCH a.addresses addr
        WHERE a.role = :role
    """)
    List<Account> findAllByRoleWithAddresses(@Param("role") RoleName role);

    @Query("""
        SELECT DISTINCT a FROM Account a 
        LEFT JOIN FETCH a.addresses addr
        WHERE a.role = :role
          AND a.enabled = :enabled
    """)
    List<Account> findByRoleAndEnabledWithAddresses(@Param("role") RoleName role,
                                                    @Param("enabled") boolean enabled);

}
