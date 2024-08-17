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

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(operationId = "create", summary = "Создать пользователя")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);
        return ResponseEntity.ok(response);
    }

    @Operation(operationId = "deactivate", summary = "Деактивировать анкету пользователя")
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID userId
    ) {
        userService.deactivate(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "activate", summary = "Активировать анкету пользователя")
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID userId) {
        userService.activate(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "get", summary = "Получить детали пользователя")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(
            @PathVariable UUID userId
    ) {
        UserResponse response = userService.get(userId);
        return ResponseEntity.ok(response);
    }
}