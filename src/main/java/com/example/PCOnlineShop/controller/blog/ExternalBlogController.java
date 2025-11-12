package com.example.PCOnlineShop.controller.blog;

import com.example.PCOnlineShop.dto.blog.BlogLinkDto;
import com.example.PCOnlineShop.service.blog.HacomScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blog")
public class ExternalBlogController {

    private final HacomScraperService scraperService;

    // 🔹 Hiển thị danh sách bài viết HACOM
    @GetMapping("/hacom")
    public String showHacom(Model model) {
        List<BlogLinkDto> links = scraperService.getLatest();
        model.addAttribute("links", links);
        model.addAttribute("sourceName", "HACOM");
        model.addAttribute("sourceUrl", "https://hacom.vn/tin-tuc");
        return "blog/hacom-list";
    }

    // 🔹 Mapping /blog để nút header dẫn vào
    @GetMapping
    public String showBlogHome(Model model) {
        // Redirect tạm thời sang HACOM, hoặc bạn có thể tạo trang blog tổng hợp riêng
        return "redirect:/blog/hacom";
    }
}
