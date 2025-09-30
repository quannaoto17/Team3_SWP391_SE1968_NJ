package com.example.PCOnlineShop.model.warranty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderView {
    private int orderId;
    private LocalDate createdDate;
    private BigDecimal totalValue;

    public OrderView(int orderId, LocalDate createdDate, BigDecimal totalValue) {
        this.orderId = orderId;
        this.createdDate = createdDate;
        this.totalValue = totalValue;
    }
    public int getOrderId() { return orderId; }
    public LocalDate getCreatedDate() { return createdDate; }
    public BigDecimal getTotalValue() { return totalValue; }
}
