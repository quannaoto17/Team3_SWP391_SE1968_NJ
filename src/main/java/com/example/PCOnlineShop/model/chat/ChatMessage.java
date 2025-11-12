package com.example.PCOnlineShop.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String sender;   // "USER" hoặc "AI"
    private String message;
    private LocalDateTime sentAt = LocalDateTime.now();
}
