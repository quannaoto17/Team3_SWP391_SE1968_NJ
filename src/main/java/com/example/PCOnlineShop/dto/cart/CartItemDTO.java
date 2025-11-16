package com.example.PCOnlineShop.dto.cart;

import com.example.PCOnlineShop.model.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Data
@NoArgsConstructor
public class CartItemDTO {

    private int cartItemId;
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private int inventoryQuantity;
    private String imageUrl;
    private double subtotal;
    private boolean selected;

    public CartItemDTO(Product product, int quantity) {
        if (product != null) {
            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.price = product.getPrice();
            this.inventoryQuantity = product.getInventoryQuantity();
            this.quantity = quantity;

            if (Hibernate.isInitialized(product.getImages()) &&
                    product.getImages() != null &&
                    !product.getImages().isEmpty()) {
                this.imageUrl = product.getImages().get(0).getImageUrl();
            } else {
                this.imageUrl = "/images/no-image.png";
            }
            calculateSubtotal();
        }
    }

    public void calculateSubtotal() {
        this.subtotal = this.price * this.quantity;
    }

    public double getSubtotal() {
        return price * quantity;
    }
}