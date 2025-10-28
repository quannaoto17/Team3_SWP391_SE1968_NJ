package com.example.PCOnlineShop.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutDTO {

    // BỎ @NotBlank
    private String shippingFullName;
    private String shippingPhone;
    private String shippingAddress;

    // Giữ @NotBlank cho shippingMethod
    @jakarta.validation.constraints.NotBlank(message = "Shipping method is required")
    private String shippingMethod;

    private String note;
}