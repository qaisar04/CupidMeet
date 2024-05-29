package ru.polskiy.feedbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.polskiy.feedbackservice.dto.ComplaintReviewDto;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Review;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.model.repository.ReviewRepository;
import ru.polskiy.feedbackservice.service.FeedBackService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedBackController {
    private final ComplaintRepository complaintRepository;
    private final ReviewRepository reviewRepository;
    private final FeedBackService feedBackService;

    @GetMapping("/complains")
    public List<Complaint> getComplains(){
        return complaintRepository.findAll();
    }
    @GetMapping("/reviews")
    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }
    @PostMapping("/complain/create")
    public ResponseEntity<?> complainCreate(@RequestBody ComplaintReviewDto complaintReviewDto){
        ComplaintReviewDto dto = feedBackService.createComplain(complaintReviewDto);
        return ResponseEntity.ok(dto);
    }
    @PostMapping("/review/create")
    public ResponseEntity<?> reviewCreate(@RequestBody ComplaintReviewDto complaintReviewDto){
        ComplaintReviewDto dto = feedBackService.createReview(complaintReviewDto);
        return ResponseEntity.ok(dto);
    }
}
