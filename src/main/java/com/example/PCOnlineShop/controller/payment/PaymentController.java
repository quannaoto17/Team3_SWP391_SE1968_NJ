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

    /**
     * Constructor để tiêm (inject) tất cả các service/repository cần thiết
     */
    public PaymentController(PaymentService paymentService,
                             OrderService orderService,
                             PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Khách hàng bị redirect về đây sau khi thanh toán thành công.
     * Dùng orderCode (timestamp) để truy vấn.
     */
    @GetMapping("/callback/success")
    public String handleSuccessCallback(@RequestParam("orderCode") long orderCode,
                                        RedirectAttributes redirectAttributes) {
        try {
            // Gọi service để truy vấn lại PayOS
            PaymentLink transaction = paymentService.queryTransaction(orderCode);

            if (transaction != null && "PAID".equals(transaction.getStatus())) {
                redirectAttributes.addFlashAttribute("success", "Thanh toán thành công! Đơn hàng đang được xử lý.");
            } else {
                redirectAttributes.addFlashAttribute("info", "Chúng tôi đang xác nhận thanh toán của bạn...");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi kiểm tra trạng thái thanh toán.");
        }
        return "redirect:/orders/list"; // Luôn chuyển về trang danh sách đơn hàng
    }

    /**
     * Khách hàng bị redirect về đây nếu hủy thanh toán.
     * Dùng orderCode (timestamp) để tìm và hủy đơn.
     */
    @GetMapping("/callback/failed")
    public String handleFailedCallback(@RequestParam(value = "orderCode", required = true) Long orderCode,
                                       RedirectAttributes redirectAttributes) {
        if (orderCode != null) {
            try {
                // 1. Tìm bản ghi Payment bằng orderCode (timestamp)
                Payment payment = paymentRepository.findByOrderCode(orderCode)
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Payment: " + orderCode));

                // 2. Gọi logic hủy (dùng payment_id nội bộ) để hoàn kho
                orderService.cancelOrderFromPaymentId(payment.getPaymentId());
            } catch (Exception e) {
                System.err.println("Lỗi khi Khách hàng hủy: " + e.getMessage());
            }
        }
        redirectAttributes.addFlashAttribute("error", "Thanh toán đã bị hủy. Đơn hàng của bạn đã được hủy.");
        return "redirect:/orders/list";
    }

    /**
     * WEBHOOK - PayOS sẽ gọi (POST) tới đây.
     * Đây là nguồn tin cậy duy nhất để cập nhật trạng thái "PAID".
     */
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