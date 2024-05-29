package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.dto.ComplaintReviewDto;
import ru.polskiy.feedbackservice.mapper.ComplaintMapper;
import ru.polskiy.feedbackservice.mapper.ReviewMapper;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.model.repository.ReviewRepository;
import ru.polskiy.feedbackservice.model.type.Status;
import ru.polskiy.feedbackservice.service.FeedBackService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedBackServiceImpl implements FeedBackService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    /**
     * this method response for creating user's complaint from dto
     * and also sets status "NEW" by default
     * @param dto users's complaint and email
     * @return the original dto
     */
    @Transactional
    @Override
    public ComplaintReviewDto createComplain(ComplaintReviewDto dto) {
        return Optional.of(dto)
                .map(complaintMapper::toEntity)
                .map(entity-> {
                 entity.setStatus(Status.NEW);
                 return entity;
                })
                .map(complaintRepository::save)
                .map(complaintMapper::toDto)
                .orElseThrow();
    }

    /**
     *this method response for creating user's review from dto
     * @param dto users's complaint and email
     * @return the original dto
     */
    @Override
    @Transactional
    public ComplaintReviewDto createReview(ComplaintReviewDto dto) {
        return Optional.of(dto)
                .map(reviewMapper::toEntity)
                .map(reviewRepository::save)
                .map(reviewMapper::toDto)
                .orElseThrow();
    }

}
