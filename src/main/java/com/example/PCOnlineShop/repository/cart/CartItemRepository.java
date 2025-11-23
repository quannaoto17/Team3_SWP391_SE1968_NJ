package com.example.PCOnlineShop.repository.cart;

import com.example.PCOnlineShop.model.cart.Cart;
import com.example.PCOnlineShop.model.cart.CartItem;
import com.example.PCOnlineShop.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);


    List<CartItem> findByCartAndIsSelected(Cart cart, boolean isSelected);

    @Transactional
    void deleteByCartAndIsSelected(Cart cart, boolean isSelected);

    @Transactional
    void deleteByCart(Cart cart);
}