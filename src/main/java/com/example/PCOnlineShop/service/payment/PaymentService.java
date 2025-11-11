package com.example.PCOnlineShop.service.payment;

import com.example.PCOnlineShop.dto.payment.PaymentInfoDTO;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import com.example.PCOnlineShop.service.order.OrderService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;

// (Các import này là đúng, dựa trên code mẫu của bạn)
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLink;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;
import vn.payos.model.webhooks.WebhookData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    private final PayOS payOS;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PayOS payOS, OrderRepository orderRepository, PaymentRepository paymentRepository, OrderService orderService) {
        this.payOS = payOS;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    /**
     * Bước 1: Tạo bản ghi Payment (Giữ nguyên)
     */
    @Transactional
    public Payment createPaymentRecord(Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(BigDecimal.valueOf(order.getFinalAmount()));
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    /**
     * Bước 2: Tạo link thanh toán PayOS (SỬA LẠI LOGIC orderCode)
     */
    @Transactional
    public String createPayOSLink(Payment payment) throws Exception {

        // ✅ SỬA LỖI: Tạo orderCode duy nhất bằng timestamp (giống code mẫu)
        final long uniqueOrderCode = System.currentTimeMillis();

        // ✅ Lưu orderCode này vào CSDL
        payment.setOrderCode(uniqueOrderCode);
        paymentRepository.save(payment); // Lưu trước khi gửi

        final String description = "Thanh toan don hang #" + payment.getOrder().getOrderId();
        final String returnUrl = "http://localhost:8081/payment/callback/success";
        final String cancelUrl = "http://localhost:8081/payment/callback/failed";

        List<PaymentLinkItem> items = new ArrayList<>();
        // ... (logic thêm items)
        for (OrderDetail detail : payment.getOrder().getOrderDetails()) {
            items.add(PaymentLinkItem.builder()
                    .name(detail.getProduct().getProductName())
                    .quantity(detail.getQuantity())
                    .price((long) detail.getPrice())
                    .build());
        }

        CreatePaymentLinkRequest paymentData = CreatePaymentLinkRequest.builder()
                .orderCode(uniqueOrderCode) // ✅ Gửi orderCode duy nhất này
                .amount(payment.getAmount().longValue())
                .description(description)
                .items(items)
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl)
                .build();

        CreatePaymentLinkResponse payosResponse = payOS.paymentRequests().create(paymentData);

        String checkoutUrl = payosResponse.getCheckoutUrl();
        payment.setGatewayPaymentId(payosResponse.getPaymentLinkId());
        paymentRepository.save(payment); // Lưu lại paymentLinkId

        return checkoutUrl;
    }

    /**
     * Bước 3: Xử lý Webhook (SỬA LẠI LOGIC TÌM KIẾM)
     */
    @Transactional
    public void handleWebhook(Object body) throws Exception {

        WebhookData webhookData = payOS.webhooks().verify(body);

        // ✅ SỬA LỖI: Lấy orderCode (timestamp) từ webhook
        long orderCode = webhookData.getOrderCode();

        // ✅ SỬA LỖI: Tìm Payment bằng orderCode, không phải paymentId
        Payment payment = paymentRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Payment với orderCode: " + orderCode));

        Order order = payment.getOrder();
        String webhookType = webhookData.getCode();

        if ("00".equals(webhookType)) {
            // ... (logic SUCCESS)
            payment.setStatus("SUCCESS");
            payment.setGatewayPaymentId(webhookData.getPaymentLinkId());
            payment.setRawPayload(webhookData.toString());
            order.setPaymentStatus("PAID");
            order.setStatus("Processing");
            order.setPaidAt(LocalDateTime.now());

        } else {
            if ("PENDING".equals(payment.getStatus())) {
                // ... (logic FAILED)
                orderService.cancelOrderFromPaymentId(payment.getPaymentId()); // Gọi hàm cancel đã có
            }
        }
    }
    /**
     * MỚI: Lấy thông tin thanh toán cho View
     */
    public PaymentInfoDTO getPaymentInfoByOrderId(long orderId) {
        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin thanh toán cho đơn hàng: " + orderId));

        return new PaymentInfoDTO(payment);
    }

    /**
     * ✅ BƯỚC 4: TRUY VẤN GIAO DỊCH (PHƯƠNG THỨC BỊ THIẾU)
     * (Dùng cho trang callback)
     */
    public PaymentLink queryTransaction(long orderCode) throws Exception {
        try {
            // Dùng API v2 để truy vấn thông tin link thanh toán
            return payOS.paymentRequests().get(orderCode);
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn PayOS: " + e.getMessage());
            return null;
        }
    }

}