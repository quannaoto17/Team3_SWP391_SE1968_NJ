package com.example.PCOnlineShop.dto.order;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderSearchRequest {

    @Pattern(regexp = "^$|\\d+", message = "Phone number must contain only digits.")
    private String searchPhone;
}