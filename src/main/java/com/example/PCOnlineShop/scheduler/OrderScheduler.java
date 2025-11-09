package com.example.PCOnlineShop.scheduler;

import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import com.example.PCOnlineShop.repository.payment.PaymentRepository;
import com.example.PCOnlineShop.service.order.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderScheduler {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository; // (Có thể xóa nếu không dùng)
    private final OrderService orderService; // ✅ THÊM

    // ✅ CẬP NHẬT CONSTRUCTOR
    public OrderScheduler(PaymentRepository paymentRepository, OrderRepository orderRepository,
                          OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService; // ✅ THÊM
    }

    /**
     * Chạy mỗi 60 giây (60000ms) để tìm và hủy các đơn hàng quá hạn 15 phút.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredPendingOrders() {
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minusMinutes(3);
        List<Payment> expiredPayments = paymentRepository.findPendingPaymentsOlderThan(fifteenMinutesAgo);

        if (!expiredPayments.isEmpty()) {
            System.out.println("Scheduler: Tìm thấy " + expiredPayments.size() + " đơn hàng quá hạn 3 phút.");

            for (Payment payment : expiredPayments) {
                // ✅ GỌI LOGIC HỦY VÀ HOÀN KHO TẬP TRUNG
                try {
                    orderService.cancelOrderFromPaymentId(payment.getPaymentId());
                } catch (Exception e) {
                    System.err.println("Lỗi khi Scheduler hủy đơn: " + e.getMessage());
                }
            }
        }
    }
}