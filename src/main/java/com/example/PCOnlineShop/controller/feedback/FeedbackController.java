package com.example.PCOnlineShop.controller.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import com.example.PCOnlineShop.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /** ================= LIST FEEDBACK ================= */
    @GetMapping
    public String list(
            @RequestParam(required = false) String dateSort,
            @RequestParam(required = false) String ratingSort,
            Model model
    ) {

        String sortKey = "pendingFirst";   // default

   /*     if (ratingSort != null && !ratingSort.isBlank()) {
            sortKey = ratingSort;
        } else if (dateSort != null && !dateSort.isBlank()) {
            sortKey = dateSort;
        }*/

        List<Feedback> data = feedbackService.findAllNoPaging(sortKey);

        model.addAttribute("data", data);
        model.addAttribute("dateSort", dateSort);
        model.addAttribute("ratingSort", ratingSort);

        return "feedback/feedback-list";
    }


    /** ================= VIEW FEEDBACK ================= */
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id,
                         @RequestParam(required = false) String back,
                         Model model) {

        Feedback fb = feedbackService.get(id);
        model.addAttribute("fb", fb);
        model.addAttribute("back", back);
        return "feedback/feedback-detail";
    }

    /** ================= REPLY ================= */
    @PostMapping("/{id}/reply")
    public String reply(@PathVariable Integer id,
                        @RequestParam String reply,
                        @RequestParam(required = false) String back,
                        RedirectAttributes ra) {
        // Gọi service để cập nhật reply
        feedbackService.updateReply(id, reply);
        ra.addFlashAttribute("msg", "Đã phản hồi feedback thành công.");

        return "redirect:/staff/feedback";
    }

}
