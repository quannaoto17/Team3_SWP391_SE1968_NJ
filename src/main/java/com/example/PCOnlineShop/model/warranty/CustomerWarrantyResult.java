package com.example.PCOnlineShop.model.warranty;

import java.util.List;
import java.util.Map;

public class CustomerWarrantyResult {
    private String customerName; // null nếu không tìm thấy
    private List<OrderView> orders;
    private Map<Integer, List<OrderItemView>> itemsByOrderId;

    public CustomerWarrantyResult(String customerName, List<OrderView> orders,
                                  Map<Integer, List<OrderItemView>> itemsByOrderId) {
        this.customerName = customerName;
        this.orders = orders;
        this.itemsByOrderId = itemsByOrderId;
    }
    public String getCustomerName() { return customerName; }
    public List<OrderView> getOrders() { return orders; }
    public Map<Integer, List<OrderItemView>> getItemsByOrderId() { return itemsByOrderId; }
}
