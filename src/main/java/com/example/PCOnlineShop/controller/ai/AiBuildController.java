package com.example.PCOnlineShop.controller.ai;

import com.example.PCOnlineShop.dto.ai.AiPcBuildCriteria;
import com.example.PCOnlineShop.dto.ai.AiPcBuildRequest;
import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.service.ai.AiSuggestionService;
import com.example.PCOnlineShop.service.ai.PcBuilderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

/**
 * Controller cho chức năng AI PC Build Suggestion
 * ĐÂY LÀ PHIÊN BẢN PHÔI
 */
@Controller
@RequestMapping("/build/ai")
@RequiredArgsConstructor
@Slf4j
public class AiBuildController {

    private final AiSuggestionService aiSuggestionService;
    private final PcBuilderService pcBuilderService;

    /**
     * Hiển thị trang nhập yêu cầu AI suggestion
     */
    @GetMapping("/suggest")
    public String showSuggestPage(Model model) {
        model.addAttribute("request", new AiPcBuildRequest());
        return "build/ai-suggest";
    }

    /**
     * Xử lý yêu cầu và tạo cấu hình PC
     */
    @PostMapping("/suggest")
    public String processSuggestion(
            @ModelAttribute AiPcBuildRequest request,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            log.info("Processing AI suggestion: {}", request.getRequirement());

            // Step 1: Phân tích yêu cầu → Tạo tiêu chí
            AiPcBuildCriteria criteria = aiSuggestionService.analyzeBuildRequirement(request);

            // Step 2: Tạo build từ tiêu chí
            BuildItemDto suggestedBuild = pcBuilderService.buildFromCriteria(criteria);

            // Step 3: Tính tổng giá
            double totalPrice = pcBuilderService.calculateTotalPrice(suggestedBuild);

            // Lưu vào session
            session.setAttribute("aiSuggestedBuild", suggestedBuild);
            session.setAttribute("aiCriteria", criteria);

            // Hiển thị kết quả
            model.addAttribute("build", suggestedBuild);
            model.addAttribute("criteria", criteria);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("request", request);

            return "build/ai-result";

        } catch (Exception e) {
            log.error("Error processing AI suggestion: ", e);
            redirectAttributes.addFlashAttribute("error",
                "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/build/ai/suggest";
        }
    }

    /**
     * Chấp nhận cấu hình AI và chuyển sang trang finish
     */
    @PostMapping("/accept")
    public String acceptSuggestion(
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            BuildItemDto suggestedBuild = (BuildItemDto) session.getAttribute("aiSuggestedBuild");

            if (suggestedBuild == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy cấu hình.");
                return "redirect:/build/ai/suggest";
            }

            // Lưu vào buildItems session
            session.setAttribute("buildItems", suggestedBuild);

            redirectAttributes.addFlashAttribute("message",
                "Cấu hình đã được chấp nhận! Bạn có thể xem lại hoặc hoàn tất.");

            return "redirect:/build/finish";

        } catch (Exception e) {
            log.error("Error accepting suggestion: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi: " + e.getMessage());
            return "redirect:/build/ai/suggest";
        }
    }
}

