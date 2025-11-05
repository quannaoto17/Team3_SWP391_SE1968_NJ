package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductFeedbackController {

    private final FeedbackService feedbackService;
    private final AccountRepository accountRepository;

    /**  Customer gửi feedback ở trang chi tiết sản phẩm */
    @PostMapping("/detail/{id}/feedback")
    public String submitFeedback(
            @PathVariable("id") Integer id,
            @RequestParam(value = "rating", required = false) Integer rating,
            @RequestParam(value = "comment", required = false) String comment,
            Principal principal,
            RedirectAttributes ra
    ) {
        // Bắt buộc đăng nhập
        if (principal == null) {
            ra.addFlashAttribute("feedback_error", "Vui lòng đăng nhập để gửi đánh giá.");
            return "redirect:/auth/login";
        }

        //  Lấy số điện thoại của người dùng đang đăng nhập
        String phoneNumber = principal.getName();
        Account account = accountRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);

        if (account == null) {
            ra.addFlashAttribute("feedback_error", "Không tìm thấy tài khoản hợp lệ.");
            return "redirect:/auth/login";
        }

        Integer accountId = account.getAccountId();

        //  Kiểm tra dữ liệu form
        if (rating == null || comment == null || comment.trim().isEmpty()) {
            ra.addFlashAttribute("feedback_error", "Vui lòng chọn sao và nhập nội dung trước khi gửi.");
            return "redirect:/product/detail/" + id;
        }

        //  Gửi feedback
        try {
            feedbackService.createFeedback(id, accountId, rating, comment);
            ra.addFlashAttribute("feedback_success", "Gửi đánh giá thành công! Bình luận của bạn đang chờ duyệt.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("feedback_error", e.getMessage());
        }

        return "redirect:/product/detail/" + id;
    }
}
