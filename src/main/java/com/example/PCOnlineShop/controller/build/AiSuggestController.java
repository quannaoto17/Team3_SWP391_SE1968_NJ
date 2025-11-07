package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildPlanDto;
import com.example.PCOnlineShop.service.build.AiSuggestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/build")
@RequiredArgsConstructor
@Slf4j
public class AiSuggestController {

    private final AiSuggestService aiSuggestService;

    /**
     * Test AI connection endpoint
     * @return Test message from AI
     */
    @GetMapping("/ai-test")
    public ResponseEntity<String> testConnection() {
        try {
            String result = aiSuggestService.testConnection();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("AI test failed", e);
            return ResponseEntity.internalServerError()
                    .body("AI connection failed: " + e.getMessage());
        }
    }

    /**
     * Get AI suggestion for PC build based on user requirements
     * @param request Map containing "userRequest" string
     * @return BuildPlanDto with component rules
     */
    @PostMapping("/ai-suggest")
    public ResponseEntity<?> suggestBuild(@RequestBody Map<String, String> request) {
        try {
            String userRequest = request.get("userRequest");

            if (userRequest == null || userRequest.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "User request cannot be empty"));
            }

            log.info("Received AI suggest request: {}", userRequest);

            // Use actual AI service
            BuildPlanDto buildPlan = aiSuggestService.suggestBuildPlan(userRequest);

            return ResponseEntity.ok(buildPlan);

        } catch (Exception e) {
            log.error("Error processing AI suggest request", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to process request: " + e.getMessage()));
        }
    }
}

