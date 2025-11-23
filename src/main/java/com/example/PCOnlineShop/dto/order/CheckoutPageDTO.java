package com.example.PCOnlineShop.dto.order;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.account.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutPageDTO {
    private CheckoutDTO checkoutDTO;
    private List<CartItemDTO> selectedItems;
    private double grandTotal;
    private List<Address> allAddresses;
    private Address defaultAddress;
}