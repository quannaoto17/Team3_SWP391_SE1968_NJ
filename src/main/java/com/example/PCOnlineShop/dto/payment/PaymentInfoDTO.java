package com.example.PCOnlineShop.dto.payment;
import com.example.PCOnlineShop.model.payment.Payment;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentInfoDTO {
    private String gatewayPaymentId; // Mã giao dịch PayOS
    private BigDecimal amount;
    private String status; // SUCCESS, FAILED, PENDING
    private Date createdAt; // Thời gian tạo

    public PaymentInfoDTO(Payment payment) {
        this.gatewayPaymentId = payment.getGatewayPaymentId();
        this.amount = payment.getAmount();
        this.status = payment.getStatus();
        this.createdAt = payment.getCreatedAt();
    }
}