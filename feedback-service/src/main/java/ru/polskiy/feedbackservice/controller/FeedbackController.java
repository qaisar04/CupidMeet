package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.FeedbackCreateDTO;
import ru.polskiy.feedbackservice.dto.FeedbackRequestDTO;
import ru.polskiy.feedbackservice.mapper.ComplaintMapper;
import ru.polskiy.feedbackservice.mapper.FeedbackMapper;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @GetMapping("/all")
    public List<FeedbackCreateDTO> findFeedbacks(
    ) {
        return feedbackService.findAllFeedbacks()
                .stream()
                .map(feedbackMapper::toDto)
                .toList();
    }

    @PostMapping("/create/{userId}")
    public FeedbackCreateDTO feedbackCreate(
            @RequestBody FeedbackRequestDTO dto,
            @PathVariable Long userId
    ) {
        return Optional.of(new FeedbackCreateDTO(userId, dto.comment()))
                .map(feedbackMapper::toEntity)
                .map(feedbackService::createFeedback)
                .map(feedbackMapper::toDto)
                .orElse(null);
    }
}