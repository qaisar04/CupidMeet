package ru.polskiy.feedbackservice.controller;

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
    @PostMapping
    public ResponseEntity<Void> feedbackCreate(
            @Valid @RequestBody FeedbackCreateRequest createDto
    ) {
        feedbackService.createFeedback(feedbackMapper.toEntity(createDto));
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the feedback for the specified user.
     * <p>
     * This method handles PATCH requests to update the comment and grade of a feedback entry
     * for the user with the given ID. It utilizes the FeedbackRequestResponse object to obtain
     * the new comment and grade values.
     *
     * @param userId  The ID of the user whose feedback is to be updated
     * @param request The FeedbackRequestResponse object containing the new comment and grade
     * @return A ResponseEntity with a status of 200 (OK) if the update is successful
     */
    @PatchMapping("/update/{userId}")
    public ResponseEntity<Void> updateFeedback(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody FeedbackRequestResponse request
    ) {
        feedbackService.updateFeedback(userId, request);
        return ResponseEntity.ok().build();
    }
}