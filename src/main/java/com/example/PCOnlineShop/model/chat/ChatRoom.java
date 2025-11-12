package com.example.PCOnlineShop.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private Integer id;
    private Integer userId;
    private List<ChatMessage> messages = new ArrayList<>();

    // Constructor chỉ với id, messages tự khởi tạo
    public ChatRoom(Integer id) {
        this.id = id;
        this.messages = new ArrayList<>();
    }

    // Constructor với id + userId, messages tự khởi tạo
    public ChatRoom(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
        this.messages = new ArrayList<>();
    }
}
