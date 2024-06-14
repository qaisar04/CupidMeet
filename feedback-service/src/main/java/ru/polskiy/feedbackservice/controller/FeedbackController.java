package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.UserComplaintDTO;
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
    private final ComplaintMapper complaintMapper;
    private final FeedbackMapper feedbackMapper;

    @GetMapping("/complaints")
    public List<UserComplaintDTO> findComplaints() {
        return feedbackService.findAllComplaints()
                .stream()
                .map(complaintMapper::toDto)
                .toList();
    }

    @GetMapping("/feedbacks")
    public List<FeedbackRequestDTO> findFeedbacks() {
        return feedbackService.findAllFeedbacks()
                .stream()
                .map(feedbackMapper::toDto)
                .toList();
    }

    @PostMapping("/complaint/create/{userId}")
    public UserComplaintDTO complaintCreate(@RequestBody String comment, @PathVariable Long userId) {

        return Optional.of(new UserComplaintDTO(userId,
                        comment,
                null,
                null))
                .map(complaintMapper::toEntity)
                .map(feedbackService::createComplaint)
                .map(complaintMapper::toDto)
                .orElse(null);
    }

    @PostMapping("/feedback/create/{userId}")
    public FeedbackRequestDTO feedbackCreate(@RequestBody String comment, @PathVariable Long userId) {
        return Optional.of(new FeedbackRequestDTO(userId, comment))
                .map(feedbackMapper::toEntity)
                .map(feedbackService::createFeedback)
                .map(feedbackMapper::toDto)
                .orElse(null);
        //TODO resolve why returning json
    }
}