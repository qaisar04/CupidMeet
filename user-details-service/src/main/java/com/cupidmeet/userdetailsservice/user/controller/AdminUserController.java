package com.cupidmeet.userdetailsservice.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import com.cupidmeet.userdetailsservice.user.domain.types.Role;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Контроллер для управления пользователями со стороны администратора.
 */
@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    /**
     * Удалить пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return HTTP-ответ с кодом 200 при успешном удалении
     */
    @Operation(operationId = "deleteUser", summary = "Удалить пользователя")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId) {
        userService.setSignDeleted(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Заблокировать пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return HTTP-ответ с кодом 200 при успешной блокировке
     */
    @Operation(operationId = "blockUser", summary = "Блокировать пользователя")
    @PatchMapping("/block/{userId}")
    public ResponseEntity<Void> block(@PathVariable UUID userId) {
        userService.block(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Разблокировать пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return HTTP-ответ с кодом 200 при успешной блокировке
     */
    @Operation(operationId = "unblockUser", summary = "Разблокировать пользователя")
    @PatchMapping("/unblock/{userId}")
    public ResponseEntity<Void> unblock(@PathVariable UUID userId) {
        userService.unblock(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Назначить пользователю новую роль.
     *
     * @param adminId идентификатор администратора, выполняющего действие
     * @param userId идентификатор пользователя
     * @param role роль, которую нужно назначить
     * @return HTTP-ответ с кодом 200 при успешном назначении роли
     */
    @Operation(operationId = "assignRole", summary = "Назначить новую роль пользователю")
    @PatchMapping("/assign-role/{adminId}/{userId}")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID adminId,
            @PathVariable UUID userId,
            @RequestParam(required = false, defaultValue = "ADMIN") String role
    ) {
        userService.assignRole(adminId, userId, Role.valueOf(role));
        return ResponseEntity.ok().build();
    }
}