package com.example.PCOnlineShop.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSummaryDTO {
    private List<CartItemDTO> items;
    private double selectedTotal;
}