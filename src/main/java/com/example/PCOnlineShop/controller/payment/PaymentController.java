package com.example.PCOnlineShop.controller.payment;

import com.example.PCOnlineShop.dto.payment.PaymentInfoDTO;
import com.example.PCOnlineShop.service.order.OrderService;
import com.example.PCOnlineShop.service.payment.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.payos.model.v2.paymentRequests.PaymentLink; // ✅ SỬA IMPORT

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService; //

    //
    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService; // ✅ THÊM
    }

    /**
     * Khách hàng bị redirect về đây sau khi thanh toán
     */
    @GetMapping("/callback/success")
    public String handleSuccessCallback(@RequestParam("orderCode") long paymentId,
                                        RedirectAttributes redirectAttributes) {
        try {
            // ✅ SỬA LỖI: Nhận về đối tượng PaymentLink
            PaymentLink transaction = paymentService.queryTransaction(paymentId);

            // ✅ SỬA LỖI: Kiểm tra 'status' của PaymentLink
            if (transaction != null && "PAID".equals(transaction.getStatus())) {
                redirectAttributes.addFlashAttribute("success", "Thanh toán thành công! Đơn hàng đang được xử lý.");
            } else {
                redirectAttributes.addFlashAttribute("info", "Chúng tôi đang xác nhận thanh toán của bạn...");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi kiểm tra trạng thái thanh toán.");
        }
        return "redirect:/orders/list"; // Về trang danh sách đơn hàng
    }


    /**
     * WEBHOOK
     */
    @PostMapping("/webhook")
    @ResponseBody
    public ResponseEntity<String> handlePayOSWebhook(@RequestBody Object body) { // ✅ SỬA LỖI: Nhận Object
        try {
            System.out.println("--- PAYOS WEBHOOK RECEIVED ---");
            // System.out.println(body.toString()); // In ra (nếu cần)

            // Chuyển 'body' (có thể là Map hoặc String) cho service
            paymentService.handleWebhook(body);

            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            System.err.println("Webhook Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Khách hàng bị redirect về đây nếu hủy thanh toán
     */
    @GetMapping("/callback/failed")
    public String handleFailedCallback(@RequestParam(value = "orderCode", required = false) Long paymentId,
                                       RedirectAttributes redirectAttributes) {

        if (paymentId != null) {
            try {
                // ✅ GỌI ORDER SERVICE ĐỂ HỦY VÀ HOÀN KHO
                orderService.cancelOrderFromPaymentId(paymentId);
            } catch (Exception e) {
                // Bỏ qua lỗi (ví dụ: đơn đã được xử lý)
                System.err.println("Lỗi khi Khách hàng hủy: " + e.getMessage());
            }
        }
        redirectAttributes.addFlashAttribute("error", "Thanh toán đã bị hủy. Đơn hàng của bạn đã được hủy.");
        return "redirect:/orders/list";
    }

    /**
     * MỚI: Endpoint cho AJAX để lấy thông tin thanh toán
     */
    @GetMapping("/info/{orderId}")
    @ResponseBody //  Trả về JSON
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