package com.cupidmeet.userdetailsservice.user.controller;

import com.cupidmeet.userdetailsservice.user.domain.dto.FileAttachmentRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserCreateRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserResponse;
import com.cupidmeet.userdetailsservice.user.domain.entity.FileAttachment;
import com.cupidmeet.userdetailsservice.user.mapper.FileAttachmentMapper;
import com.cupidmeet.userdetailsservice.user.service.FileAttachmentService;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для управления пользователями.
 */
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileAttachmentService fileAttachmentService;
    private final FileAttachmentMapper fileAttachmentMapper;

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

    /**
     * Удалить вложение файла по его идентификатору.
     *
     * @param attachmentId идентификатор вложения файла
     * @return HTTP-ответ с кодом 200 при успешном удалении
     */
    @Operation(operationId = "removeAttachment", summary = "Удалить вложение файла")
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> removeAttachment(@PathVariable UUID attachmentId) {
        fileAttachmentService.removeAttachment(attachmentId);
        return ResponseEntity.ok().build();
    }

    /**
     * Добавить новое вложение файла для пользователя.
     *
     * @param userId идентификатор пользователя
     * @param request данные для создания вложения файла
     * @return HTTP-ответ с кодом 200 при успешном добавлении
     */
    @Operation(operationId = "addAttachment", summary = "Добавить вложение файла для пользователя")
    @PostMapping("/{userId}/attachments")
    public ResponseEntity<Void> addAttachment(@PathVariable UUID userId, @Valid @RequestBody FileAttachmentRequest request) {
        FileAttachment fileAttachment = fileAttachmentMapper.toEntity(request);
        fileAttachmentService.addAttachment(userId, fileAttachment);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить информацию о пользователях по их идентификаторам.
     *
     * @param userIds идентификаторы пользователей
     * @return информация о пользователях
     */
    @Operation(operationId = "getUsers", summary = "Получить информацию о пользователях")
    @PostMapping("/batch")
    public ResponseEntity<Map<UUID, UserResponse>> get(Collection<UUID> userIds) {
        return ResponseEntity.ok(userService.get(userIds));
    }
}