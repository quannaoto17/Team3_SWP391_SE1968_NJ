package com.example.PCOnlineShop.model.cart;

import com.example.PCOnlineShop.model.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity = 1; // Số lượng mặc định

    @Column(name = "is_selected", nullable = false)
    private boolean isSelected = true;

    @Column(name = "is_build_item", nullable = false)
    private boolean isBuildItem = false;

    @Column(name = "build_id")
    private String buildId;

}