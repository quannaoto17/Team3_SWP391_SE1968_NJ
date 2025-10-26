package com.example.PCOnlineShop.repository.cart;

import com.example.PCOnlineShop.model.cart.Cart;
import com.example.PCOnlineShop.model.cart.CartItem;
import com.example.PCOnlineShop.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    // Tìm một item cụ thể trong một cart dựa vào product
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}