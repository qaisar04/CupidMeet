package com.cupidmeet.userdetailsservice.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import com.cupidmeet.userdetailsservice.user.domain.types.Role;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @Operation(operationId = "delete", summary = "Удалить пользователя")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "block", summary = "Блокировать пользователя")
    @PatchMapping("/block/{userId}")
    public ResponseEntity<Void> block(@PathVariable UUID userId) {
        userService.block(userId);
        return ResponseEntity.ok().build();
    }

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