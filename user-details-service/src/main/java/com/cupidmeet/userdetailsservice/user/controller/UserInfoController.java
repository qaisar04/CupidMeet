package com.cupidmeet.userdetailsservice.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;
import com.cupidmeet.userdetailsservice.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Operation(summary = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User information not found")
    })
    @PostMapping("{userId}/update")
    public ResponseEntity<Void> update(
            @Valid @RequestBody UserInfoRequest userInfo,
            @PathVariable("userId") UUID userId
    ) {
        userInfoService.update(userInfo, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add attachment to user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("{userId}/attachments")
    public ResponseEntity<Void> addAttachment(
            @PathVariable UUID userId,
            @RequestBody Set<String> fileIds
    ) {
        userInfoService.addAttachment(userId, fileIds);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove attachment from user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment removed successfully"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    @DeleteMapping("{userId}/attachments")
    public ResponseEntity<Void> removeAttachment(
            @PathVariable UUID userId,
            @RequestBody Set<String> fileIds
    ) {
        userInfoService.removeAttachment(userId, fileIds);
        return ResponseEntity.ok().build();
    }
}