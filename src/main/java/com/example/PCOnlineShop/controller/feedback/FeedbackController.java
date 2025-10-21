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

    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "ALL") String status,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDesc") String sort,
            Model model
    ) {
        Page<Feedback> p = feedbackService.search(keyword, status, rating, from, to, page, size, sort);

        model.addAttribute("data", p);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("rating", rating);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);

        // build qs để giữ filter khi chuyển trang / quay về
        String qs = buildQS(keyword, status, rating, from, to, size, sort);
        model.addAttribute("qs", qs);
        return "feedback/feedback-list";
    }

    @PostMapping("/bulk-status")
    public String bulkStatus(@RequestParam("id") Integer[] ids,
                             @RequestParam("st") String[] statuses,
                             @RequestParam("qs") String qs,
                             RedirectAttributes ra) {
        Map<Integer, String> payload = new HashMap<>();
        for (int i = 0; i < ids.length; i++) payload.put(ids[i], statuses[i]);
        feedbackService.bulkUpdateStatus(payload);
        ra.addFlashAttribute("msg", "Đã lưu thay đổi trạng thái.");
        return "redirect:/staff/feedback" + (qs == null ? "" : ("?" + qs));
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id,
                         @RequestParam(required = false) String back, // chuỗi query quay lại list
                         Model model) {
        Feedback fb = feedbackService.get(id);
        model.addAttribute("fb", fb);
        model.addAttribute("back", back); // có thể null
        return "feedback/feedback-detail";
    }

    @PostMapping("/{id}/reply")
    public String reply(@PathVariable Integer id,
                        @RequestParam String reply,
                        @RequestParam(required = false) String back,
                        RedirectAttributes ra) {
        feedbackService.updateReply(id, reply);
        ra.addFlashAttribute("msg", "Đã cập nhật phản hồi.");
        // yêu cầu: reply xong → về lại list
        String target = (back == null || back.isBlank()) ? "/staff/feedback" : ("/staff/feedback?" + back);
        return "redirect:" + target;
    }

    private String buildQS(String keyword, String status, Integer rating,
                           LocalDate from, LocalDate to, int size, String sort) {
        String enc = StandardCharsets.UTF_8.name();
        StringBuilder sb = new StringBuilder();
        if (keyword != null && !keyword.isBlank())
            sb.append("keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8)).append("&");
        if (status != null) sb.append("status=").append(status).append("&");
        if (rating != null) sb.append("rating=").append(rating).append("&");
        if (from != null) sb.append("from=").append(from).append("&");
        if (to != null) sb.append("to=").append(to).append("&");
        sb.append("size=").append(size).append("&");
        sb.append("sort=").append(sort);
        return sb.toString();
    }
}
