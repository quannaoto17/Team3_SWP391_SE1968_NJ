package com.example.PCOnlineShop.service.warranty;

import com.example.PCOnlineShop.dto.warranty.WarrantyDTO;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.repository.warranty.WarrantyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    public List<Order> getOrdersByPhone(String phone) {
        return warrantyRepository.findOrdersByPhone(phone);
    }

    public List<WarrantyDTO> getProductsInOrder(int orderId) {
        List<Object[]> results = warrantyRepository.findWarrantyByOrderId(orderId);
        return results.stream().map(r -> new WarrantyDTO(
                (String) r[0],
                ((Number) r[1]).intValue(),
                (Integer) r[2],
                ((java.sql.Date) r[3]).toLocalDate()
        )).toList();
    }
}
