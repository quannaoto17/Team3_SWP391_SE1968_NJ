package com.example.PCOnlineShop.service.chat;

import com.example.PCOnlineShop.model.chat.ChatMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIChatService {

    private static final String GEMINI_API_URL = "https://gemini-api-endpoint.com/v1/chat";
    private static final String API_KEY = "AIzaSyB28XF6GshGfabYexEiQbeyxqXyQrw3I_8";

    public ChatMessage getAIResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("prompt", userMessage);
        body.put("model", "gemini-1.5");
        body.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_API_URL, request, Map.class);

        String aiText = response.getBody().get("text").toString();

        return new ChatMessage("AI", aiText, LocalDateTime.now());
    }
}
