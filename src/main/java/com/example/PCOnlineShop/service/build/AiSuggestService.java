package com.example.PCOnlineShop.service.build;

import com.example.PCOnlineShop.dto.build.BuildPlanDto;
import com.example.PCOnlineShop.dto.build.ComponentRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AiSuggestService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructor - Spring AI sẽ tự động inject ChatClient.Builder
     */
    public AiSuggestService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Phân tích yêu cầu build PC và trả về BuildPlanDto
     * @param userRequest Yêu cầu của khách hàng (ví dụ: "máy tính chơi game cấu hình cao, ngân sách 30 triệu")
     * @return BuildPlanDto với các ComponentRule cho từng linh kiện
     */
    public BuildPlanDto suggestBuildPlan(String userRequest) {
        log.info("🤖 AI analyzing build request: {}", userRequest);

        try {
            // 1. Phân tích yêu cầu để lấy budget và purpose
            Map<String, Object> analyzedRequest = analyzeUserRequest(userRequest);
            double totalBudget = (double) analyzedRequest.get("budget");
            String purpose = (String) analyzedRequest.get("purpose");

            log.info("📊 Analyzed - Budget: {} VND, Purpose: {}", totalBudget, purpose);

            // 2. Tạo prompt template
            String promptTemplate = createPromptTemplate();

            // 3. Tạo prompt với parameters
            PromptTemplate template = new PromptTemplate(promptTemplate);
            Prompt prompt = template.create(Map.of(
                "request", userRequest,
                "budget", String.format("%.0f", totalBudget),
                "purpose", purpose
            ));

            // 4. Gọi AI
            String aiResponse = chatClient.prompt(prompt)
                    .call()
                    .content();

            log.info("🎯 AI Response received: {}", aiResponse);

            // 5. Parse JSON response
            BuildPlanDto buildPlan = parseAiResponse(aiResponse);

            log.info("✅ Build plan created successfully");
            return buildPlan;

        } catch (Exception e) {
            log.error("❌ Error in AI suggestion process", e);
            // Fallback to basic plan
            return createFallbackPlan(userRequest);
        }
    }

    /**
     * Phân tích yêu cầu của user để lấy budget và purpose
     */
    private Map<String, Object> analyzeUserRequest(String userRequest) {
        String lowerRequest = userRequest.toLowerCase();

        // Extract budget (hỗ trợ nhiều format)
        double budget = extractBudget(lowerRequest);

        // Determine purpose
        String purpose = determinePurpose(lowerRequest);

        return Map.of(
            "budget", budget,
            "purpose", purpose
        );
    }

    /**
     * Extract budget từ user request
     */
    private double extractBudget(String request) {
        // Pattern cho USD với dấu $
        Pattern dollarPattern = Pattern.compile("\\$\\s*(\\d+(?:,\\d{3})*(?:\\.\\d{2})?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = dollarPattern.matcher(request);

        if (matcher.find()) {
            String amount = matcher.group(1).replace(",", "");
            return Double.parseDouble(amount);
        }

        // Pattern cho số với USD hoặc dollar text
        Pattern usdPattern = Pattern.compile("(\\d+(?:,\\d{3})*)\\s*(?:usd|dollar|dollars)", Pattern.CASE_INSENSITIVE);
        matcher = usdPattern.matcher(request);

        if (matcher.find()) {
            String amount = matcher.group(1).replace(",", "");
            return Double.parseDouble(amount);
        }

        // Pattern cho số trực tiếp (3-4 digits, assume USD)
        Pattern directPattern = Pattern.compile("\\b(\\d{3,4})\\b", Pattern.CASE_INSENSITIVE);
        matcher = directPattern.matcher(request);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        // Default budget
        return 1200; // $1200 USD
    }

    /**
     * Determine purpose từ user request
     */
    private String determinePurpose(String request) {
        if (request.contains("game") || request.contains("gaming")) {
            if (request.contains("high") || request.contains("4k") || request.contains("enthusiast")) {
                return "gaming-high";
            }
            if (request.contains("stream")) {
                return "streaming";
            }
            return "gaming-mid";
        }

        if (request.contains("render") || request.contains("3d") ||
            request.contains("video edit") || request.contains("workstation") ||
            request.contains("content creation")) {
            return "workstation";
        }

        if (request.contains("office") || request.contains("work") ||
            request.contains("productivity") || request.contains("business")) {
            return "office";
        }

        if (request.contains("budget") || request.contains("cheap") ||
            request.contains("affordable") || request.contains("value")) {
            return "budget";
        }

        return "general"; // General purpose
    }

    /**
     * Tạo prompt template cho AI
     */
    private String createPromptTemplate() {
        return """
            You are a top PC building expert with 20 years of experience and deep knowledge of computer hardware.
            
            Customer Requirements:
            - Detailed request: "{request}"
            - Total budget: ${budget} USD
            - Purpose: {purpose}
            
            Please analyze and create a PC building plan by dividing the budget and suggesting 
            performance scores (0-100 scale) for ALL 8 components:
            1. CPU (Processor)
            2. GPU (Graphics Card)
            3. Motherboard
            4. Memory (RAM)
            5. Storage (SSD/HDD)
            6. PSU (Power Supply)
            7. Case (PC Case)
            8. Cooling (CPU Cooler)
            
            IMPORTANT GUIDELINES:
            
            A. Budget Distribution (budgetMax in USD):
            - Gaming High-End: GPU 40-45%, CPU 20-25%, RAM 10%, Storage 8%, PSU 8%, Motherboard 7%, Cooling 4%, Case 3%
            - Gaming Mid-Range: GPU 35-40%, CPU 20%, RAM 12%, Storage 10%, PSU 8%, Motherboard 7%, Cooling 3%, Case 3%
            - Workstation: CPU 30%, GPU 25%, RAM 15%, Storage 15%, PSU 8%, Motherboard 5%, Cooling 4%, Case 3%
            - Office: CPU 25%, RAM 15%, Storage 20%, Motherboard 15%, GPU 10%, PSU 8%, Case 4%, Cooling 3%
            - Budget: Balanced, prioritize CPU and RAM, integrated GPU if possible
            - Streaming: GPU 35%, CPU 25%, RAM 12%, Storage 10%, PSU 8%, Motherboard 7%, Cooling 4%, Case 3%
            
            B. Performance Score Ranges (scoreMin, scoreMax):
            - Higher score = more powerful and premium components
            - Gaming High: CPU 80-95, GPU 85-100, RAM 75-90, Storage 70-85
            - Gaming Mid: CPU 70-85, GPU 75-90, RAM 65-80, Storage 60-75
            - Workstation: CPU 85-100, GPU 75-90, RAM 80-95, Storage 75-90
            - Office: CPU 50-70, GPU 30-50, RAM 50-70, Storage 50-70
            - Budget: All 40-60
            - For Case, PSU, Cooling: Usually 60-80 is sufficient
            
            C. Mandatory Rules:
            1. Total budgetMax of 8 components MUST equal ${budget} USD
            2. scoreMin must be less than scoreMax
            3. Score range (scoreMax - scoreMin) should be 10-20 for flexibility
            4. All values must be positive numbers
            
            RETURN RESULT IN EXACT JSON FORMAT (no additional text):
            {
              "planCpu": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planGpu": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planMotherboard": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planRam": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planStorage": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planPsu": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planCase": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              },
              "planCooling": {
                "budetMax": <amount in USD>,
                "scoreMin": <minimum score>,
                "scoreMax": <maximum score>
              }
            }
            
            RETURN ONLY JSON, NO EXPLANATION.
            """;
    }

    /**
     * Parse AI response JSON to BuildPlanDto
     */
    private BuildPlanDto parseAiResponse(String aiResponse) {
        try {
            // Extract JSON from response (in case AI adds extra text)
            String jsonContent = extractJson(aiResponse);

            // Parse JSON to BuildPlanDto
            return objectMapper.readValue(jsonContent, BuildPlanDto.class);

        } catch (JsonProcessingException e) {
            log.error("Failed to parse AI response as JSON", e);
            throw new RuntimeException("Invalid AI response format", e);
        }
    }

    /**
     * Extract JSON from AI response (remove markdown formatting if any)
     */
    private String extractJson(String response) {
        // Remove markdown code blocks if present
        response = response.replaceAll("```json\\s*", "");
        response = response.replaceAll("```\\s*", "");

        // Find JSON object
        int startIndex = response.indexOf('{');
        int endIndex = response.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1) {
            return response.substring(startIndex, endIndex + 1);
        }

        return response.trim();
    }

    /**
     * Create fallback plan when AI fails
     */
    private BuildPlanDto createFallbackPlan(String userRequest) {
        log.warn("⚠️ Using fallback plan");

        String purpose = determinePurpose(userRequest.toLowerCase());
        double budget = extractBudget(userRequest.toLowerCase());

        // Default distribution based on purpose
        Map<String, Double> distribution;
        Map<String, int[]> scoreRanges;

        switch (purpose) {
            case "gaming-high":
                distribution = Map.of(
                    "cpu", 0.22, "gpu", 0.42, "mainboard", 0.07, "ram", 0.10,
                    "storage", 0.08, "psu", 0.08, "case", 0.03, "cooling", 0.04
                );
                scoreRanges = Map.of(
                    "cpu", new int[]{80, 95}, "gpu", new int[]{85, 100},
                    "mainboard", new int[]{70, 85}, "ram", new int[]{75, 90},
                    "storage", new int[]{70, 85}, "psu", new int[]{75, 90},
                    "case", new int[]{65, 80}, "cooling", new int[]{70, 85}
                );
                break;

            case "workstation":
                distribution = Map.of(
                    "cpu", 0.30, "gpu", 0.25, "mainboard", 0.05, "ram", 0.15,
                    "storage", 0.15, "psu", 0.07, "case", 0.03, "cooling", 0.04
                );
                scoreRanges = Map.of(
                    "cpu", new int[]{85, 100}, "gpu", new int[]{75, 90},
                    "mainboard", new int[]{70, 85}, "ram", new int[]{80, 95},
                    "storage", new int[]{75, 90}, "psu", new int[]{75, 90},
                    "case", new int[]{60, 75}, "cooling", new int[]{70, 85}
                );
                break;

            case "office":
                distribution = Map.of(
                    "cpu", 0.25, "gpu", 0.10, "mainboard", 0.15, "ram", 0.15,
                    "storage", 0.20, "psu", 0.08, "case", 0.04, "cooling", 0.03
                );
                scoreRanges = Map.of(
                    "cpu", new int[]{50, 70}, "gpu", new int[]{30, 50},
                    "mainboard", new int[]{50, 70}, "ram", new int[]{50, 70},
                    "storage", new int[]{50, 70}, "psu", new int[]{60, 75},
                    "case", new int[]{50, 65}, "cooling", new int[]{50, 65}
                );
                break;

            default: // gaming-mid or general
                distribution = Map.of(
                    "cpu", 0.20, "gpu", 0.38, "mainboard", 0.07, "ram", 0.12,
                    "storage", 0.10, "psu", 0.08, "case", 0.03, "cooling", 0.03
                );
                scoreRanges = Map.of(
                    "cpu", new int[]{70, 85}, "gpu", new int[]{75, 90},
                    "mainboard", new int[]{65, 80}, "ram", new int[]{65, 80},
                    "storage", new int[]{60, 75}, "psu", new int[]{70, 85},
                    "case", new int[]{60, 75}, "cooling", new int[]{65, 80}
                );
        }

        return BuildPlanDto.builder()
                .planCpu(createRule(budget * distribution.get("cpu"), scoreRanges.get("cpu")))
                .planGpu(createRule(budget * distribution.get("gpu"), scoreRanges.get("gpu")))
                .planMotherboard(createRule(budget * distribution.get("mainboard"), scoreRanges.get("mainboard")))
                .planRam(createRule(budget * distribution.get("ram"), scoreRanges.get("ram")))
                .planStorage(createRule(budget * distribution.get("storage"), scoreRanges.get("storage")))
                .planPsu(createRule(budget * distribution.get("psu"), scoreRanges.get("psu")))
                .planCase(createRule(budget * distribution.get("case"), scoreRanges.get("case")))
                .planCooling(createRule(budget * distribution.get("cooling"), scoreRanges.get("cooling")))
                .build();
    }

    /**
     * Helper method to create ComponentRule
     */
    private ComponentRule createRule(double budget, int[] scoreRange) {
        return new ComponentRule(budget, scoreRange[0], scoreRange[1]);
    }

    /**
     * Test AI connection
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
