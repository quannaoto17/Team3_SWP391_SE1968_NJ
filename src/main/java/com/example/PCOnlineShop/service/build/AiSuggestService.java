package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildPlanDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiSuggestService {

    private final ChatClient chatClient;
    private final BeanOutputConverter<BuildPlanDto> outputConverter;

    /**
     * Constructor - Spring AI sẽ tự động inject ChatClient.Builder
     */
    public AiSuggestService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.outputConverter = new BeanOutputConverter<>(BuildPlanDto.class);
    }

    /**
     * Phân tích yêu cầu build PC và trả về BuildPlanDto
     * @param userRequest Yêu cầu của khách hàng (ví dụ: "máy tính chơi game cấu hình cao, ngân sách 30 triệu")
     * @return BuildPlanDto với các ComponentRule cho từng linh kiện
     */
//    public BuildPlanDto suggestBuildPlan(String userRequest) {
//        log.info("Suggesting build plan for: {}", userRequest);
//    }

    /**
     * Tạo prompt cho AI với format output
     */

    public String testConnection() {
        try {
            return chatClient.prompt()
                    .user("Say 'Hello from AI!' in Vietnamese")
                    .call()
                    .content();
        } catch (Exception e) {
            return "Connection failed: " + e.getMessage();
        }
    }
}
