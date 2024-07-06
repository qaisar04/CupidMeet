package ru.polskiy.feedbackservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.FeedbackCreateRequest;
import ru.polskiy.feedbackservice.dto.FeedbackRequestResponse;
import ru.polskiy.feedbackservice.mapper.FeedbackMapper;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;

/**
 * Controller for managing feedback with REST API.
 * It allows retrieving, creating, and updating feedback.
 */
@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    /**
     * Retrieves a list of all feedbacks.
     *
     * @return A ResponseEntity containing a list of {@link FeedbackRequestResponse} representing all feedbacks.
     */
    @Operation(summary = "Get all feedbacks", description = "Retrieves a list of all feedbacks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of feedbacks", content = @Content(schema = @Schema(implementation = FeedbackRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<FeedbackRequestResponse>> findFeedbacks() {
        return ResponseEntity.ok(
                feedbackService.findAllFeedbacks()
                        .stream()
                        .map(feedbackMapper::toResponse)
                        .toList());
    }

    /**
     * Creates a new feedback.
     *
     * @param createDto The request body containing the necessary information for creating feedback.
     * @return A ResponseEntity with HTTP status 200 (OK) if the creation is successful.
     */
    @Operation(summary = "Create a new feedback", description = "Creates a new feedback based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the feedback", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> feedbackCreate(
            @Valid @RequestBody FeedbackCreateRequest createDto
    ) {
        feedbackService.createFeedback(feedbackMapper.toEntity(createDto));
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the feedback for the specified user.
     *
     * This method handles PATCH requests to update the comment and grade of a feedback entry
     * for the user with the given ID. It utilizes the FeedbackRequestResponse object to obtain
     * the new comment and grade values.
     *
     * @param userId  The ID of the user whose feedback is to be updated
     * @param request The FeedbackRequestResponse object containing the new comment and grade
     * @return A ResponseEntity with a status of 200 (OK) if the update is successful
     */
    @Operation(summary = "Update feedback for a user", description = "Updates the comment and grade of a feedback entry for the user with the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the feedback", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/update/{userId}")
    public ResponseEntity<Void> updateFeedback(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody FeedbackRequestResponse request
    ) {
        feedbackService.updateFeedback(userId, request);
        return ResponseEntity.ok().build();
    }
}