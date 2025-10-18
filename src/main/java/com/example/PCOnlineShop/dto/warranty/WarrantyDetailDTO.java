package com.example.PCOnlineShop.dto.warranty; // Đảm bảo đúng package

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date; // Thêm Date cho orderDate

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyDetailDTO { // Đổi tên class
    private int orderId;        // Thêm orderId
    private String productName;
    private Date orderDate;     // Thêm orderDate
    private int warrantyMonths;
    private LocalDate expiryDate;
    private String status;

}