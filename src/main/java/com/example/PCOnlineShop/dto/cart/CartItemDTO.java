package com.example.PCOnlineShop.dto.cart;

import com.example.PCOnlineShop.model.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Data
@NoArgsConstructor
public class CartItemDTO {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private int inventoryQuantity; // Số lượng tồn kho
    private String imageUrl;
    private double subtotal;

    // Constructor để tạo DTO từ Product và số lượng trong giỏ
    public CartItemDTO(Product product, int quantity) {
        if (product != null) {
            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.price = product.getPrice();
            // Lấy tồn kho từ model Product (bạn đã thêm cột inventory_quantity)
            this.inventoryQuantity = product.getInventoryQuantity();
            this.quantity = quantity;

            // Kiểm tra an toàn với Hibernate.isInitialized()
            // Chỉ truy cập images nếu đã được load, tránh LazyInitializationException
            if (Hibernate.isInitialized(product.getImages()) &&
                product.getImages() != null &&
                !product.getImages().isEmpty()) {
                this.imageUrl = product.getImages().get(0).getImageUrl();
            } else {
                this.imageUrl = "/images/no-image.png"; // Ảnh mặc định
            }

            calculateSubtotal(); // Tính thành tiền
        }
    }

    // Tính thành tiền
    public void calculateSubtotal() {
        this.subtotal = this.price * this.quantity;
    }

}