package com.example.PCOnlineShop.repository.order;

import com.example.PCOnlineShop.constant.RoleName;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.account a WHERE a.role = :role",
            countQuery = "SELECT count(o) FROM Order o WHERE o.account.role = :role")
    Page<Order> findAllByRole(RoleName role, Pageable pageable);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.account a WHERE a.role = :role AND a.phoneNumber = :phoneNumber",
            countQuery = "SELECT count(o) FROM Order o WHERE o.account.role = :role AND o.account.phoneNumber = :phoneNumber")
    Page<Order> findByRoleAndPhoneNumber(RoleName role, String phoneNumber, Pageable pageable);

    List<Order> findByAccount(Account account);

    @Query("SELECT o FROM Order o JOIN FETCH o.account a WHERE a.phoneNumber = :phoneNumber ORDER BY o.createdDate DESC")
    List<Order> findByAccount_PhoneNumber(String phoneNumber);

    @Query("SELECT o FROM Order o JOIN FETCH o.account cust WHERE o.status IN :statuses ORDER BY o.createdDate ASC")
    List<Order> findByStatusIn(@Param("statuses") List<String> statuses);

    @Query(value = "SELECT o FROM Order o JOIN FETCH o.account a WHERE a.role = :role ORDER BY o.createdDate DESC")
    List<Order> findAllByRole(@Param("role") RoleName role);

    long countByStatus(String status);
}