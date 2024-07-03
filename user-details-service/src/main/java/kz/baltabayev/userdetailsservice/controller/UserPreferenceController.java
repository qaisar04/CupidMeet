package kz.baltabayev.userdetailsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.baltabayev.userdetailsservice.model.dto.UserMatchResponse;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/preference")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    /**
     * Endpoint for creating a new user preference.
     *
     * @param gender The gender preference of the user.
     * @param userId The ID of the user for whom the preference is being created.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Create user preference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User preference created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("{userId}")
    public ResponseEntity<Void> create(
            @RequestParam("gender") String gender,
            @PathVariable("userId") Long userId
    ) {
        userPreferenceService.create(userId, gender);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for updating a user's preference.
     *
     * @param userId The ID of the user whose preference is being updated.
     * @param gender The updated gender preference of the user.
     * @param minAge The updated minimum age preference of the user.
     * @param maxAge The updated maximum age preference of the user.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Update user preference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User preference updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User preference not found")
    })
    @PatchMapping("{userId}/update")
    public ResponseEntity<Void> update(
            @PathVariable("userId") Long userId,
            @RequestParam("gender") String gender,
            @RequestParam("minAge") Integer minAge,
            @RequestParam("minAge") Integer maxAge
    ) {
        userPreferenceService.update(userId, gender, minAge, maxAge);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{userId}/users")
    public ResponseEntity<List<UserMatchResponse>> getMatchingUsers(@PathVariable Long userId, @RequestParam(required = false) Set<Long> userIds) {
        return ResponseEntity.ok(userPreferenceService.findMatchingUsers(userId, userIds));
    }
}