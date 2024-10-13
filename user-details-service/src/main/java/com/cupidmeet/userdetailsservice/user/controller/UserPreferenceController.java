package com.cupidmeet.userdetailsservice.user.controller;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserMatchResponse;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserPreferenceRequest;
import com.cupidmeet.userdetailsservice.user.service.UserPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для управления предпочтениями пользователей.
 */
@RestController
@RequestMapping("/v1/preference")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    /**
     * Обновить информацию о предпочтениях пользователя.
     *
     * @param userId идентификатор пользователя
     * @param userPreferenceRequest объект, содержащий новые предпочтения пользователя
     * @return HTTP-ответ с кодом 200 при успешном обновлении
     */
    @Operation(operationId = "updateUserPreference", summary = "Обновить информацию пользователя")
    @PutMapping("{userId}")
    public ResponseEntity<Void> update(
            @PathVariable("userId") UUID userId,
            @RequestBody UserPreferenceRequest userPreferenceRequest
    ) {
        userPreferenceService.update(userId, userPreferenceRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить список подходящих пользователей для указанного пользователя.
     *
     * @param userId идентификатор пользователя, для которого ищутся совпадения
     * @return HTTP-ответ с кодом 200 и списком подходящих пользователей
     */
    @Operation(operationId = "getMatchingUsers", summary = "Получить подходящих пользователей для определенного пользователя")
    @GetMapping("{userId}/users")
    public ResponseEntity<List<UserMatchResponse>> getMatchingUsers(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(userPreferenceService.findMatchingUsers(userId));
    }

    @Operation(operationId = "getMatchingUsersTest", summary = "Получить подходящих пользователей для определенного пользователя (Тестовый)")
    @GetMapping("test/{userId}/users")
    public ResponseEntity<List<UserMatchResponse>> getMatchingUsersTest(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(userPreferenceService.findMatchingUsersTest(userId));
    }
}