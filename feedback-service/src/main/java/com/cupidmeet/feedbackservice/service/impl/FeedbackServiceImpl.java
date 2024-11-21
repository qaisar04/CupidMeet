package com.cupidmeet.feedbackservice.service.impl;

import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackCreateRequest;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackResponse;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackUpdateRequest;
import com.cupidmeet.feedbackservice.domain.entity.Feedback;
import com.cupidmeet.feedbackservice.mapper.FeedbackMapper;
import com.cupidmeet.feedbackservice.repository.FeedbackRepository;
import com.cupidmeet.feedbackservice.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cupidmeet.feedbackservice.message.Messages.DUPLICATE_FEEDBACK_SUBMISSION;
import static com.cupidmeet.feedbackservice.message.Messages.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Transactional
    @Override
    public void create(FeedbackCreateRequest request) {
        Feedback feedback = feedbackMapper.toEntity(request);

        boolean exists = feedbackRepository.existsByUserId(feedback.getUserId());
        if (exists) {
            throw new CustomRuntimeException(DUPLICATE_FEEDBACK_SUBMISSION);
        }

        feedbackRepository.save(feedback);
    }

    @Override
    public List<FeedbackResponse> get() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            return Collections.emptyList();
        }

        return feedbacks.stream()
                .map(feedbackMapper::toResponse)
                .toList();
    }

    @Override
    public FeedbackResponse update(UUID id, FeedbackUpdateRequest request) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);
        if (optionalFeedback.isEmpty()) {
            throw new CustomRuntimeException(NOT_FOUND, "Отзыв", "идентификатором", id);
        }

        Feedback existingFeedback = optionalFeedback.get();
        feedbackMapper.updateEntityFromDto(request, existingFeedback);
        feedbackRepository.save(existingFeedback);

        return get(id);
    }

    private FeedbackResponse get(UUID id) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(id);
        if (optionalFeedback.isEmpty()) {
            throw new CustomRuntimeException(NOT_FOUND, "Отзыв", "идентификатором", id);
        }

        return feedbackMapper.toResponse(optionalFeedback.get());
    }
}