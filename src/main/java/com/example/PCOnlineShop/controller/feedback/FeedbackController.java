package com.example.PCOnlineShop.controller.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import com.example.PCOnlineShop.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**  Danh sách feedback (mặc định hiển thị Pending) */
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "Pending") String status,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDesc") String sort,
            Model model
    ) {
        Page<Feedback> data = feedbackService.search(status, rating, from, to, page, size, sort);

        model.addAttribute("data", data);
        model.addAttribute("status", status);
        model.addAttribute("rating", rating);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("qs", buildQS(status, rating, from, to, size, sort));

        return "feedback/feedback-list";
    }

    /**  Cập nhật hàng loạt */
    @PostMapping("/bulk-status")
    public String bulkStatus(
            @RequestParam("id") Integer[] ids,
            @RequestParam("st") String[] statuses,
            @RequestParam("qs") String qs,
            RedirectAttributes ra
    ) {
        Map<Integer, String> payload = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            payload.put(ids[i], statuses[i]);
        }

        feedbackService.bulkUpdateStatus(payload);
        ra.addFlashAttribute("msg", "Đã duyệt phản hồi (Allow).");

        return "redirect:/staff/feedback?status=Pending";
    }

    /**  Xem chi tiết feedback */
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id,
                         @RequestParam(required = false) String back,
                         Model model) {
        Feedback fb = feedbackService.get(id);
        model.addAttribute("fb", fb);
        model.addAttribute("back", back);
        return "feedback/feedback-detail";
    }

    /**  Staff phản hồi feedback */
    @PostMapping("/{id}/reply")
    public String reply(@PathVariable Integer id,
                        @RequestParam String reply,
                        @RequestParam(required = false) String back,
                        RedirectAttributes ra) {
        feedbackService.updateReply(id, reply);
        feedbackService.updateStatus(id, "Allow");
        ra.addFlashAttribute("msg", "Đã phản hồi và duyệt feedback.");
        return "redirect:/staff/feedback?status=Pending";
    }

    /**  Build Query String giữ lại filter */
    private String buildQS(String status, Integer rating,
                           LocalDate from, LocalDate to, int size, String sort) {
        StringBuilder sb = new StringBuilder();
        if (status != null)
            sb.append("status=").append(URLEncoder.encode(status, StandardCharsets.UTF_8)).append("&");
        if (rating != null)
            sb.append("rating=").append(rating).append("&");
        if (from != null)
            sb.append("from=").append(from).append("&");
        if (to != null)
            sb.append("to=").append(to).append("&");
        sb.append("size=").append(size).append("&");
        sb.append("sort=").append(sort);
        return sb.toString();
    }
}
