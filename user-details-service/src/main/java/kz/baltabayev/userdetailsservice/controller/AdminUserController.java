package kz.baltabayev.userdetailsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.baltabayev.userdetailsservice.model.types.Role;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling administrative operations related to users.
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    /**
     * Deletes a user by the provided user ID.
     *
     * @param userId The ID of the user to delete
     * @return ResponseEntity indicating success or failure of the deletion operation
     */
    @Operation(summary = "Delete a user")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Blocks a user by the provided user ID.
     *
     * @param userId The ID of the user to block
     * @return ResponseEntity indicating success or failure of the block operation
     */
    @Operation(summary = "Block a user")
    @PatchMapping("/block/{userId}")
    public ResponseEntity<Void> block(@PathVariable Long userId) {
        userService.block(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Assigns a new role to a user.
     * This endpoint allows an administrator to assign a new role to a specific user.
     * The role is passed as a request parameter and defaults to ADMIN if not specified.
     *
     * @param adminId The ID of the administrator performing the role assignment.
     * @param userId  The ID of the user whose role is to be changed.
     * @param role    The new role to be assigned to the user. Defaults to ADMIN.
     * @return ResponseEntity indicating the success of the operation.
     */
    @Operation(summary = "Assign a new role to a user")
    @PatchMapping("/assign-role/{adminId}/{userId}")
    public ResponseEntity<Void> assignRole(
            @PathVariable Long adminId,
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "ADMIN") String role
    ) {
        userService.assignRole(adminId, userId, Role.valueOf(role));
        return ResponseEntity.ok().build();
    }
}