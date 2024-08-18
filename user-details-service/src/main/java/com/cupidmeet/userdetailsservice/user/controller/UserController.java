package com.cupidmeet.userdetailsservice.user.controller;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserCreateRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserResponse;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Контроллер для управления пользователями.
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Создать нового пользователя.
     *
     * @param request данные для создания пользователя
     * @return информация о созданном пользователе
     */
    @Operation(operationId = "createUser", summary = "Создать пользователя")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Деактивировать анкету пользователя.
     *
     * @param userId идентификатор пользователя
     * @return HTTP-ответ с кодом 200 при успешной деактивации
     */
    @Operation(operationId = "deactivateUser", summary = "Деактивировать анкету пользователя")
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID userId) {
        userService.deactivate(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Активировать анкету пользователя.
     *
     * @param userId идентификатор пользователя
     * @return HTTP-ответ с кодом 200 при успешной активации
     */
    @Operation(operationId = "activateUser", summary = "Активировать анкету пользователя")
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID userId) {
        userService.activate(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить детали пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return информация о пользователе
     */
    @Operation(operationId = "getUser", summary = "Получить детали пользователя")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(@PathVariable UUID userId) {
        UserResponse response = userService.get(userId);
        return ResponseEntity.ok(response);
    }
}