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

/**
 * Контроллер для управления предпочтениями пользователей.
 */
@RestController
@RequestMapping("/api/v1/preference")
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
    @PatchMapping("{userId}")
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
     * @param userIds (опционально) список идентификаторов пользователей для сравнения
     * @return HTTP-ответ с кодом 200 и списком подходящих пользователей
     */
    @Operation(operationId = "getMatchingUsers", summary = "Получить подходящих пользователей для определенного пользователя")
    @PostMapping("{userId}/users")
    public ResponseEntity<List<UserMatchResponse>> getMatchingUsers(
            @PathVariable UUID userId,
            @RequestBody(required = false) Set<UUID> userIds
    ) {
        return ResponseEntity.ok(userPreferenceService.findMatchingUsers(userId, userIds));
    }
}