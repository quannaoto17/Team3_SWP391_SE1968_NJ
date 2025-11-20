package com.example.PCOnlineShop.service.payment;

import com.example.PCOnlineShop.dto.payment.PaymentInfoDTO;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import com.example.PCOnlineShop.service.order.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLink;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;
import vn.payos.model.webhooks.WebhookData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final PayOS payOS;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PayOS payOS, PaymentRepository paymentRepository, @Lazy OrderService orderService) {
        this.payOS = payOS;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional
    public Payment createPaymentRecord(Order order) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(BigDecimal.valueOf(order.getFinalAmount()));
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    @Transactional
    public String createPayOSLink(Payment payment) throws Exception {
        final long uniqueOrderCode = System.currentTimeMillis();
        payment.setOrderCode(uniqueOrderCode);
        paymentRepository.save(payment);

        final String description = "Payment for order #" + payment.getOrder().getOrderId();
        final String returnUrl = "http://localhost:8081/payment/callback/success";
        final String cancelUrl = "http://localhost:8081/payment/callback/failed";

        List<PaymentLinkItem> items = new ArrayList<>();
        for (OrderDetail detail : payment.getOrder().getOrderDetails()) {
            items.add(PaymentLinkItem.builder()
                    .name(detail.getProduct().getProductName())
                    .quantity(detail.getQuantity())
                    .price((long) detail.getPrice())
                    .build());
        }

        CreatePaymentLinkRequest paymentData = CreatePaymentLinkRequest.builder()
                .orderCode(uniqueOrderCode)
                .amount(payment.getAmount().longValue())
                .description(description)
                .items(items)
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl)
                .build();

        CreatePaymentLinkResponse payosResponse = payOS.paymentRequests().create(paymentData);
        payment.setGatewayPaymentId(payosResponse.getPaymentLinkId());
        paymentRepository.save(payment);

        return payosResponse.getCheckoutUrl();
    }

    @Transactional
    public void handleWebhook(Object body) throws Exception {
        WebhookData webhookData = payOS.webhooks().verify(body);
        long orderCode = webhookData.getOrderCode();
        Payment payment = paymentRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new EntityNotFoundException("No information for order with orderCode: " + orderCode));
        Order order = payment.getOrder();
        String webhookType = webhookData.getCode();

        if ("00".equals(webhookType)) {
            payment.setStatus("SUCCESS");
            payment.setGatewayPaymentId(webhookData.getPaymentLinkId());
            payment.setRawPayload(webhookData.toString());
            order.setPaymentStatus("PAID");
            order.setStatus("Ready to Ship");
            order.setPaidAt(LocalDateTime.now());
        } else {
            if ("PENDING".equals(payment.getStatus())) {
                orderService.cancelOrderFromPaymentId(payment.getPaymentId());
            }
        }
    }

    public PaymentInfoDTO getPaymentInfoByOrderId(long orderId) {
        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException("No information for order: " + orderId));
        return new PaymentInfoDTO(payment);
    }

    public PaymentLink queryTransaction(long orderCode) throws Exception {
        try {
            return payOS.paymentRequests().get(orderCode);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verifyPaymentStatus(long orderCode) throws Exception {
        PaymentLink transaction = queryTransaction(orderCode);
        return transaction != null && "PAID".equals(transaction.getStatus());
    }

    public void processFailedPayment(long orderCode) {
        try {
            Payment payment = paymentRepository.findByOrderCode(orderCode)
                    .orElseThrow(() -> new EntityNotFoundException("Not found"));
            orderService.cancelOrderFromPaymentId(payment.getPaymentId());
        } catch (Exception e) {
        }
    }

    public PaymentInfoDTO getPaymentInfoSafe(long orderId) {
        try {
            return getPaymentInfoByOrderId(orderId);
        } catch (Exception e) {
            return null;
        }
    }
}