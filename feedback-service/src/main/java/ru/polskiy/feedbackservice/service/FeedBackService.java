package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.dto.ComplaintReviewDto;

public interface FeedBackService {
    ComplaintReviewDto createComplain(ComplaintReviewDto dto);
    ComplaintReviewDto createReview(ComplaintReviewDto dto);
}
