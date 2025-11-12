package com.example.PCOnlineShop.controller.chat;

import com.example.PCOnlineShop.model.chat.ChatMessage;
import com.example.PCOnlineShop.model.chat.ChatRoom;
import com.example.PCOnlineShop.service.chat.AIChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class AIChatController {

    private final Map<Integer, ChatRoom> rooms = new HashMap<>(); // lưu tạm các phòng chat
    private final AIChatService aiChatService;

    public AIChatController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    // Hiển thị trang chat
    @GetMapping("/{roomId}")
    public String chatPage(@PathVariable Integer roomId, Model model) {
        ChatRoom room = rooms.computeIfAbsent(roomId, id -> new ChatRoom(id));
        model.addAttribute("room", room);
        return "chat/chat"; // Thymeleaf sẽ tìm templates/chat/chat.html
    }

    // Gửi tin nhắn user
    @PostMapping("/send")
    public String sendMessage(@RequestParam Integer roomId,
                              @RequestParam String message) {

        // Lấy hoặc tạo room
        ChatRoom room = rooms.computeIfAbsent(roomId, id -> new ChatRoom(id));

        // Tin nhắn từ user
        ChatMessage userMsg = new ChatMessage("USER", message, LocalDateTime.now());
        room.getMessages().add(userMsg);

        // Tin nhắn từ AI
        ChatMessage aiMsg = aiChatService.getAIResponse(message);
        room.getMessages().add(aiMsg);

        // Redirect về GET để load lại chat với message mới
        return "redirect:/chat/" + roomId;
    }
}
