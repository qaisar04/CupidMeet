package com.cupidmeet.userdetailsservice.user.controller;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;
import com.cupidmeet.userdetailsservice.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}