package com.example.PCOnlineShop.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CheckoutResponseDto {
    private String checkoutUrl;
    private String paymentId;
}
