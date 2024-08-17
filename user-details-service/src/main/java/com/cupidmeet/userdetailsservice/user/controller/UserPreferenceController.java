package com.cupidmeet.userdetailsservice.user.controller;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserMatchResponse;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserPreferenceRequest;
import com.cupidmeet.userdetailsservice.user.service.UserPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/preference")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @Operation(operationId = "update", summary = "Обновить информацию пользователя")
    @PatchMapping("{userId}")
    public ResponseEntity<Void> update(
            @PathVariable("userId") UUID userId,
            @RequestBody UserPreferenceRequest userPreferenceRequest
    ) {
        userPreferenceService.update(userId, userPreferenceRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "getMatchingUsers", summary = "Получить подходящих пользователей для определенного пользователя")
    @GetMapping("{userId}/users")
    public ResponseEntity<List<UserMatchResponse>> getMatchingUsers(
            @PathVariable UUID userId,
            @RequestParam(required = false) Set<UUID> userIds //TODO: убрать из RequestParam
    ) {
        return ResponseEntity.ok(userPreferenceService.findMatchingUsers(userId, userIds));
    }
}