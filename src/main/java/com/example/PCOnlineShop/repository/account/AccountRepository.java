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
    boolean existsByPhoneNumberAndRole(String phoneNumber, RoleName role);

    /* ==================== Dùng cho DataTable (trả ALL) ==================== */

    // Không fetch address → dùng khi không cần hiển thị address
    List<Account> findAllByRole(RoleName role);

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

    /* ==================== Phân trang cũ (giữ tạm để không lỗi code khác) ==================== */
    Page<Account> findAllByRole(RoleName role, Pageable pageable);

    @Query("""
        SELECT a FROM Account a
        WHERE a.role = :role
          AND (LOWER(a.phoneNumber) LIKE LOWER(CONCAT('%', :searchQuery, '%'))
            OR LOWER(a.email) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
    """)
    Page<Account> findByPhoneNumberOrEmail(@Param("role") RoleName role,
                                           @Param("searchQuery") String searchQuery,
                                           Pageable pageable);

    @Query("""
        SELECT a FROM Account a
        WHERE a.role = :role
          AND a.enabled = :enabled
    """)
    Page<Account> findByRoleAndEnabled(@Param("role") RoleName role,
                                       @Param("enabled") Boolean enabled,
                                       Pageable pageable);
}
