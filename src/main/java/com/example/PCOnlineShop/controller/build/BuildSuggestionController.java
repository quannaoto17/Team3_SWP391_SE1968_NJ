package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildPlanDto;
import com.example.PCOnlineShop.dto.build.BuildRequestDto;
import com.example.PCOnlineShop.model.build.BuildPreset;
import com.example.PCOnlineShop.service.build.RuleBasedBuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/build")
@RequiredArgsConstructor
@Slf4j
public class BuildSuggestionController {

    private final RuleBasedBuildService buildService;

    @GetMapping("/presets")
    public ResponseEntity<List<PresetInfo>> getPresets() {
        log.info("📋 Fetching all build presets");

        List<PresetInfo> presets = Arrays.stream(BuildPreset.values())
            .map(preset -> new PresetInfo(
                preset.name(),
                preset.getName(),
                preset.getDescription(),
                preset.getSuggestedMinBudget()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(presets);
    }

    @PostMapping("/suggest")
    public ResponseEntity<?> suggestBuild(@RequestBody BuildRequestDto request) {
        log.info("🎯 Received build suggestion request: preset={}, budget={}",
                 request.getPreset(), request.getBudget());

        try {
            BuildPlanDto plan = buildService.suggestBuild(
                request.getPreset(),
                request.getBudget()
            );

            log.info("✅ Build suggestion successful");
            return ResponseEntity.ok(plan);

        } catch (IllegalArgumentException e) {
            log.error("❌ Invalid preset: {}", request.getPreset());
            return ResponseEntity.badRequest()
                .body("Invalid preset: " + request.getPreset());

        } catch (Exception e) {
            log.error("❌ Error generating build suggestion", e);
            return ResponseEntity.internalServerError()
                .body("Error generating build suggestion: " + e.getMessage());
        }
    }

    // Inner DTO class for preset information
    public record PresetInfo(
        String id,
        String name,
        String description,
        double minBudget
    ) {}
}

