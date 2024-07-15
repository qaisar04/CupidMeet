package kz.baltabayev.userdetailsservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import kz.baltabayev.userdetailsservice.mapper.UserInfoMapper;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller for handling user information related requests.
 */
@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * Endpoint for updating a user's information.
     *
     * @param userInfo The request body containing the updated user information.
     * @param userId   The ID of the user whose information is being updated.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @PostMapping("{userId}/update")
    public ResponseEntity<Void> update(
            @Valid @RequestBody UserInfoRequest userInfo,
            @PathVariable("userId") Long userId
    ) {
        userInfoService.update(userInfo, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for adding attachments to user information.
     *
     * @param userId  The ID of the user to add attachments to.
     * @param fileIds Set of file IDs representing attachments to add.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Add attachment to user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("{userId}/attachments")
    public ResponseEntity<Void> addAttachment(
            @PathVariable Long userId,
            @RequestBody Set<String> fileIds
    ) {
        userInfoService.addAttachment(userId, fileIds);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for removing attachments from user information.
     *
     * @param userId  The ID of the user to remove attachments from.
     * @param fileIds Set of file IDs representing attachments to remove.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Remove attachment from user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment removed successfully"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    @DeleteMapping("{userId}/attachments")
    public ResponseEntity<Void> removeAttachment(
            @PathVariable Long userId,
            @RequestBody Set<String> fileIds
    ) {
        userInfoService.removeAttachment(userId, fileIds);
        return ResponseEntity.ok().build();
    }
}