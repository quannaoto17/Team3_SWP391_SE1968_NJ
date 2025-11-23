package com.example.PCOnlineShop.controller.payment;

import com.example.PCOnlineShop.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/callback/success")
    public String handleSuccessCallback(@RequestParam("orderCode") long orderCode,
                                        RedirectAttributes redirectAttributes) {
        try {
            boolean isPaid = paymentService.verifyPaymentStatus(orderCode);

            if (isPaid) {
                redirectAttributes.addFlashAttribute("success", "Payment Successful. Order confirmed.");
            } else {
                redirectAttributes.addFlashAttribute("info", "Payment processing...");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error checking payment.");
        }
        return "redirect:/orders/list";
    }

    @GetMapping("/callback/failed")
    public String handleFailedCallback(@RequestParam(value = "orderCode", required = false) Long orderCode,
                                       RedirectAttributes redirectAttributes) {
        if (orderCode != null) {
            paymentService.processFailedPayment(orderCode);
        }
        redirectAttributes.addFlashAttribute("error", "Payment Cancelled.");
        return "redirect:/orders/list";
    }

    @GetMapping("/continue/{orderId}")
    public String continuePayment(@PathVariable long orderId, RedirectAttributes redirectAttributes) {
        try {
            String checkoutUrl = paymentService.getOrRegeneratePaymentUrl(orderId);
            return "redirect:" + checkoutUrl;
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Unable to retrieve payment link: " + e.getMessage());
            return "redirect:/orders/detail/" + orderId;
        }
    }

    @PostMapping("/webhook")
    @ResponseBody
    public ResponseEntity<String> handlePayOSWebhook(@RequestBody Object body) {
        try {
            paymentService.handleWebhook(body);
            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/info/{orderId}")
    @ResponseBody
    public ResponseEntity<?> getPaymentInfo(@PathVariable long orderId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentInfoByOrderId(orderId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}