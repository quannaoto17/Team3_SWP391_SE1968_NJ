package com.example.PCOnlineShop.service.warranty;

import com.example.PCOnlineShop.model.warranty.CustomerWarrantyResult;
import com.example.PCOnlineShop.model.warranty.OrderItemView;
import com.example.PCOnlineShop.model.warranty.OrderView;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class WarrantyService {

    // GIẢ LẬP dữ liệu (sau này thay bằng JPA)
    public CustomerWarrantyResult searchByPhone(String phone) {
        // Demo: nếu đúng số “0239473473” thì trả dữ liệu giống screenshot
        if ("0239473473".equals(phone)) {
            String customerName = "Nguyễn Quang Anh";

            List<OrderView> orders = List.of(
                    new OrderView(1, LocalDate.of(2025, 1, 10), new BigDecimal("100000")),
                    new OrderView(2, LocalDate.of(2025, 1, 10), new BigDecimal("200000"))
            );

            // map orderId -> list item
            Map<Integer, List<OrderItemView>> items = new HashMap<>();

            // Order 1
            items.put(1, List.of(
                    new OrderItemView("Asus core i5", 1, 12,
                            LocalDate.of(2025, 1, 10).plusMonths(12)),
                    new OrderItemView("Asus core 7", 2, 12,
                            LocalDate.of(2025, 1, 10).plusMonths(12))
            ));

            // Order 2 (thêm vài món minh hoạ)
            items.put(2, List.of(
                    new OrderItemView("SSD NVMe 1TB", 1, 24,
                            LocalDate.of(2025, 1, 10).plusMonths(24)),
                    new OrderItemView("RAM 16GB", 2, 24,
                            LocalDate.of(2025, 1, 10).plusMonths(24))
            ));

            return new CustomerWarrantyResult(customerName, orders, items);
        }

        // Các số khác: giả sử không tìm thấy
        return new CustomerWarrantyResult(null, Collections.emptyList(), Collections.emptyMap());
    }
}
