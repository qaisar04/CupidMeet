package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.ComplaintFeedbackDto;
import ru.polskiy.feedbackservice.mapper.ComplaintMapper;
import ru.polskiy.feedbackservice.mapper.FeedbackMapper;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.model.repository.FeedbackRepository;
import ru.polskiy.feedbackservice.service.FeedBackService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final ComplaintRepository complaintRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedBackService feedBackService;
    private final ComplaintMapper complaintMapper;
    private final FeedbackMapper feedbackMapper;

    @GetMapping("/complaints")
    public List<Complaint> getComplains() {
        return complaintRepository.findAll();
    }

    @GetMapping("/feedbacks")
    public List<Feedback> getFeedbacks() {
        return feedbackRepository.findAll();
    }

    @PostMapping("/complaint/create")
    public ComplaintFeedbackDto complaintCreate(@RequestBody ComplaintFeedbackDto complaintFeedbackDto) {
        return feedBackService.createComplaint(complaintMapper.toEntity(complaintFeedbackDto));
    }

    @PostMapping("/feedback/create/{username}")
    public ComplaintFeedbackDto feedbackCreate(@RequestBody String comment, @PathVariable String username) {
        return feedBackService.createFeedback(feedbackMapper.toEntity(new ComplaintFeedbackDto(username, comment)));
    }
}
