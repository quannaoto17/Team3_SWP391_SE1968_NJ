package com.example.PCOnlineShop.controller.payment;

import com.example.PCOnlineShop.dto.payment.PaymentInfoDTO;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import com.example.PCOnlineShop.service.order.OrderService;
import com.example.PCOnlineShop.service.payment.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.payos.model.v2.paymentRequests.PaymentLink;

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentService paymentService,
                             OrderService orderService,
                             PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Khách hàng bị redirect về đây sau khi thanh toán thành công.
     */
    @GetMapping("/callback/success")
    public String handleSuccessCallback(@RequestParam("orderCode") long orderCode,
                                        RedirectAttributes redirectAttributes) {
        try {
            // Gọi service để truy vấn lại PayOS
            PaymentLink transaction = paymentService.queryTransaction(orderCode);

            if (transaction != null && "PAID".equals(transaction.getStatus())) {
                redirectAttributes.addFlashAttribute("success", "Successful Payment. Please wait for order confirmation.");
            } else {
                redirectAttributes.addFlashAttribute("info", "In confirmation...");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error while checking payment.");
        }
        return "redirect:/orders/list";
    }

    /**
     * Khách hàng bị redirect về đây nếu hủy thanh toán.
     */
    @GetMapping("/callback/failed")
    public String handleFailedCallback(@RequestParam(value = "orderCode", required = true) Long orderCode,
                                       RedirectAttributes redirectAttributes) {
        if (orderCode != null) {
            try {

                Payment payment = paymentRepository.findByOrderCode(orderCode)
                        .orElseThrow(() -> new EntityNotFoundException("No Found Payment: " + orderCode));

                orderService.cancelOrderFromPaymentId(payment.getPaymentId());
            } catch (Exception e) {
                System.err.println("Error when Customer cancelled: " + e.getMessage());
            }
        }
        redirectAttributes.addFlashAttribute("error", "Payment Cancelled.");
        return "redirect:/orders/list";
    }

    @PostMapping("/webhook")
    @ResponseBody
    public ResponseEntity<String> handlePayOSWebhook(@RequestBody Object body) {
        try {
            System.out.println("--- PAYOS WEBHOOK RECEIVED ---");
            paymentService.handleWebhook(body);
            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            System.err.println("Webhook Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint cho AJAX để lấy thông tin thanh toán (View Payment Info).
     * Dùng orderId (ID của CSDL).
     */
    @GetMapping("/info/{orderId}")
    @ResponseBody
    public ResponseEntity<?> getPaymentInfo(@PathVariable long orderId) {
        try {
            PaymentInfoDTO paymentInfo = paymentService.getPaymentInfoByOrderId(orderId);
            return ResponseEntity.ok(paymentInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}