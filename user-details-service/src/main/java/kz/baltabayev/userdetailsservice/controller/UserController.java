package kz.baltabayev.userdetailsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.baltabayev.userdetailsservice.mapper.UserMapper;
import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user-related requests.
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Endpoint for creating a new user.
     *
     * @param request The request body containing the user details.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody UserCreateRequest request
    ) {
        userService.create(request.id(), request.username());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for deactivating a user.
     *
     * @param userId The ID of the user to deactivate.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Deactivate a user")
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long userId
    ) {
        userService.deactivate(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for retrieving a user's details.
     *
     * @param userId The ID of the user to retrieve.
     * @return A ResponseEntity containing the user's details.
     */
    @Operation(summary = "Get user details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(
            @PathVariable Long userId
    ) {
        User user = userService.get(userId);
        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }
}