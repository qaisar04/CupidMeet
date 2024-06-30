package ru.polskiy.feedbackservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.FeedbackCreateRequest;
import ru.polskiy.feedbackservice.dto.FeedbackResponse;
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
     * @return A ResponseEntity containing a list of {@link FeedbackResponse} representing all feedbacks.
     */
    @GetMapping("/all")
    public ResponseEntity<List<FeedbackResponse>> findFeedbacks() {
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
    @PostMapping("/create")
    public ResponseEntity<Void> feedbackCreate(
            @Valid @RequestBody FeedbackCreateRequest createDto
    ) {
        feedbackService.createFeedback(feedbackMapper.toEntity(createDto));
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing feedback.
     *
     * @param createDto The request body containing updated feedback data.
     * @return A ResponseEntity with HTTP status 200 (OK) if the update is successful.
     */
    @PatchMapping("/update")
    public ResponseEntity<Void> updateFeedback(@Valid @RequestBody FeedbackCreateRequest createDto) {
        feedbackService.updateFeedback(feedbackMapper.toEntity(createDto));
        return ResponseEntity.ok().build();
        //TODO may be change @RequestBody FeedbackCreateRequest to FeedbackResponse,
        // because we need only comment and grade in fact to update this entity
    }
}
