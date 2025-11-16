package com.example.PCOnlineShop.repository.cart;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT DISTINCT c FROM Cart c LEFT JOIN FETCH c.items ci LEFT JOIN FETCH ci.product p WHERE c.account = :account AND c.status = 'Active'")
    Optional<Cart> findByAccountAndStatusWithItems(@Param("account") Account account);

    Optional<Cart> findByAccount(Account account);
}