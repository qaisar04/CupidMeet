package com.cupidmeet.feedbackservice.controller;

import com.cupidmeet.feedbackservice.domain.dto.FeedbackCreateRequest;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackResponse;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackUpdateRequest;
import com.cupidmeet.feedbackservice.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(operationId = "createFeedback", summary = "Создать новый отзыв")
    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody FeedbackCreateRequest request
    ) {
        feedbackService.create(request);
        return ResponseEntity.ok().build();
    }

    @Operation(operationId = "feedbackGetList", summary = "Получить список всех отзывов")
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> get() {
        return ResponseEntity.ok(feedbackService.get());
    }

    @Operation(operationId = "updateComplaint", summary = "Обновить существующий отзыв")
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> update(@PathVariable UUID id, FeedbackUpdateRequest request) {
        FeedbackResponse response = feedbackService.update(id, request);
        return ResponseEntity.ok(response);
    }
}