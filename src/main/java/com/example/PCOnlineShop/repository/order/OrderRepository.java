package com.example.PCOnlineShop.repository.order;

import com.example.PCOnlineShop.constant.RoleName; // Thêm import
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // SỬA ĐỔI: Lọc theo role là enum
    @Query(value = "SELECT o FROM Order o JOIN FETCH o.account a WHERE a.role = :role",
            countQuery = "SELECT count(o) FROM Order o WHERE o.account.role = :role")
    Page<Order> findAllByRole(RoleName role, Pageable pageable);

    // SỬA ĐỔI: Lọc theo role (enum) và phoneNumber
    @Query(value = "SELECT o FROM Order o JOIN FETCH o.account a WHERE a.role = :role AND a.phoneNumber = :phoneNumber",
            countQuery = "SELECT count(o) FROM Order o WHERE o.account.role = :role AND o.account.phoneNumber = :phoneNumber")
    Page<Order> findByRoleAndPhoneNumber(RoleName role, String phoneNumber, Pageable pageable);

    List<Order> findByAccount(Account account);
}
