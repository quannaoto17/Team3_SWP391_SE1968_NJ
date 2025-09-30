package com.example.PCOnlineShop.model.warranty;

import java.time.LocalDate;

public class OrderItemView {
    private String productName;
    private int quantity;
    private int warrantyMonths;
    private LocalDate expiryDate;

    public OrderItemView(String productName, int quantity, int warrantyMonths, LocalDate expiryDate) {
        this.productName = productName;
        this.quantity = quantity;
        this.warrantyMonths = warrantyMonths;
        this.expiryDate = expiryDate;
    }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public int getWarrantyMonths() { return warrantyMonths; }
    public LocalDate getExpiryDate() { return expiryDate; }
}
