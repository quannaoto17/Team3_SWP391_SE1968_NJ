package com.example.PCOnlineShop.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePaymentDto {
    private String productName;
    private String description;
    private Long price;            // tiền nguyên, giống code bạn đang dùng (eg 2000)
    private String returnUrl;
    private String cancelUrl;
}