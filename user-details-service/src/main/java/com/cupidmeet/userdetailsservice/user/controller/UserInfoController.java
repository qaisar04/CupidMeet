package com.cupidmeet.userdetailsservice.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;
import com.cupidmeet.userdetailsservice.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

/**
 * Контроллер для управления информацией о пользователях.
 */
@RestController
@RequestMapping("/v1/info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * Обновить информацию о пользователе.
     *
     * @param userInfo данные для обновления информации о пользователе
     * @param userId идентификатор пользователя
     * @return HTTP-ответ с кодом 200 при успешном обновлении
     */
    @Operation(operationId = "updateUserInfo", summary = "Обновить информацию о пользователе")
    @PutMapping("{userId}/update")
    public ResponseEntity<Void> update(
            @Valid @RequestBody UserInfoRequest userInfo,
            @PathVariable("userId") UUID userId
    ) {
        userInfoService.update(userInfo, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Добавить вложенные файлы к анкете пользователя.
     *
     * @param userId идентификатор пользователя
     * @param fileIds идентификаторы файлов, которые нужно добавить
     * @return HTTP-ответ с кодом 200 при успешном добавлении
     */
    @Operation(operationId = "addAttachment", summary = "Добавить вложенные файлы к анкете пользователя")
    @PatchMapping("{userId}/attachments")
    public ResponseEntity<Void> addAttachment(
            @PathVariable UUID userId,
            @RequestBody Set<String> fileIds
    ) {
        userInfoService.addAttachment(userId, fileIds);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить вложенные файлы с анкеты пользователя.
     *
     * @param userId идентификатор пользователя
     * @param fileIds идентификаторы файлов, которые нужно удалить
     * @return HTTP-ответ с кодом 200 при успешном удалении
     */
    @Operation(operationId = "removeAttachment", summary = "Удалить вложенные файлы с анкеты пользователя")
    @DeleteMapping("{userId}/attachments")
    public ResponseEntity<Void> removeAttachment(
            @PathVariable UUID userId,
            @RequestBody Set<String> fileIds
    ) {
        userInfoService.removeAttachment(userId, fileIds);
        return ResponseEntity.ok().build();
    }
}