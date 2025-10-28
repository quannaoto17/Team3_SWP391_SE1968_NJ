package com.example.PCOnlineShop.dto.cart;

import com.example.PCOnlineShop.model.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

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
            // Lấy ảnh đầu tiên hoặc ảnh mặc định
            this.imageUrl = (product.getImages() != null && !product.getImages().isEmpty())
                    ? product.getImages().get(0).getImageUrl() // Giả sử Image có getImageUrl()
                    : "/image/no-image.png"; // Đường dẫn tới ảnh mặc định
            calculateSubtotal(); // Tính thành tiền
        }
    }

    // Tính thành tiền
    public void calculateSubtotal() {
        this.subtotal = this.price * this.quantity;
    }

}