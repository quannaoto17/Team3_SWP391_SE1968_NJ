package com.example.PCOnlineShop.dto.warranty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyDTO {
    private String productName;
    private int quantity;
    private int warrantyMonths;
    private LocalDate expiryDate;
}
