package com.example.PCOnlineShop.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for AI PC Build request from user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiPcBuildRequest {
    private String requirement; // e.g., "máy tính chơi game cấu hình cao"
    private Double budget;      // optional budget in million VND
}

