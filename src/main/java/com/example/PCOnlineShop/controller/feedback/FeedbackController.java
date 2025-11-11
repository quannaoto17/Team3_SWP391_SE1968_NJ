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

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**  Danh sách feedback */
    @GetMapping
    public String list(
            @RequestParam(required = false, defaultValue = "Allow") String status,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9999") int size,
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
        ra.addFlashAttribute("msg", "Đã phản hồi feedback thành công.");
        return "redirect:/staff/feedback?status=Allow";
    }


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
