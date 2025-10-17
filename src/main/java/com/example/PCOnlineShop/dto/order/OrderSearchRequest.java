package com.example.PCOnlineShop.dto.order;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderSearchRequest {

    /**
     * Used to search orders by customer's phone number.
     * Rules:
     * 1. Can be empty (when not searching).
     * 2. If present, must be a string containing only digits.
     */
    @Pattern(regexp = "^$|\\d+", message = "Phone number must contain only digits.")
    private String searchPhone;
}