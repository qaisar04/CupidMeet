package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.FeedbackCreate;
import ru.polskiy.feedbackservice.dto.FeedbackRequest;
import ru.polskiy.feedbackservice.mapper.FeedbackMapper;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;
import java.util.Optional;

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
     * @return A ResponseEntity containing a list of {@link FeedbackCreate} representing all feedbacks.
     */
    @GetMapping("/all")
    public ResponseEntity<List<FeedbackCreate>> findFeedbacks() {
        return ResponseEntity.ok(
                feedbackService.findAllFeedbacks()
                        .stream()
                        .map(feedbackMapper::toDto)
                        .toList());
    }

    /**
     * Creates a new feedback.
     *
     * @param feedbackRequest The request body containing the necessary information for creating feedback.
     * @return A ResponseEntity with HTTP status 200 (OK) if the creation is successful.
     */
    @PostMapping("/create")
    public ResponseEntity<Void> feedbackCreate(
            @RequestBody FeedbackRequest feedbackRequest
    ) {
        FeedbackCreate dto = new FeedbackCreate(feedbackRequest.userId(),
                feedbackRequest.comment(), feedbackRequest.grade());
        feedbackService.createFeedback(feedbackMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing feedback.
     *
     * @param feedbackRequest The request body containing updated feedback data.
     * @return A ResponseEntity containing the updated {@link FeedbackCreate} object.
     */
    @PatchMapping("/redact")
    public ResponseEntity<FeedbackCreate> patchFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        return Optional.of(new FeedbackCreate(feedbackRequest.userId(),
                        feedbackRequest.comment(), feedbackRequest.grade()))
                .map(feedbackMapper::toEntity)
                .map(feedbackService::patchFeedback)
                .map(feedbackMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(null);
    }
}
