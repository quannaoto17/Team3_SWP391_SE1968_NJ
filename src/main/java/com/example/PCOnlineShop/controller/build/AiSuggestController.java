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

            // TODO: Implement actual AI suggestion logic
            // For now, return a mock response
            BuildPlanDto mockPlan = createMockBuildPlan();

            return ResponseEntity.ok(mockPlan);

        } catch (Exception e) {
            log.error("Error processing AI suggest request", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to process request: " + e.getMessage()));
        }
    }

    /**
     * Create a mock build plan for testing
     * TODO: Replace with actual AI service call
     */
    private BuildPlanDto createMockBuildPlan() {
        return BuildPlanDto.builder()
                .planMotherboard(createComponentRule(5000000, 70, 90))
                .planCpu(createComponentRule(8000000, 75, 95))
                .planGpu(createComponentRule(15000000, 80, 100))
                .planRam(createComponentRule(3000000, 60, 80))
                .planStorage(createComponentRule(2000000, 70, 90))
                .planPsu(createComponentRule(2500000, 75, 90))
                .planCase(createComponentRule(1500000, 60, 80))
                .planCooling(createComponentRule(1000000, 65, 85))
                .build();
    }

    /**
     * Helper method to create ComponentRule
     */
    private com.example.PCOnlineShop.dto.build.ComponentRule createComponentRule(
            double budgetMax, int scoreMin, int scoreMax) {
        return new com.example.PCOnlineShop.dto.build.ComponentRule(
                budgetMax, scoreMin, scoreMax
        );
    }
}

