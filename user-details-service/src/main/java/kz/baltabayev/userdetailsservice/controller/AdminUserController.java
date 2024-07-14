package kz.baltabayev.userdetailsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
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
}