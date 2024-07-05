package ru.polskiy.feedbackservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.dto.FeedbackRequestResponse;
import ru.polskiy.feedbackservice.exception.*;
import ru.polskiy.feedbackservice.model.entity.Feedback;
import ru.polskiy.feedbackservice.model.repository.FeedbackRepository;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public static final String NOT_FOUND_MESSAGE = "Not found feedback with id: ";

    @Transactional
    @Override
    public void createFeedback(Feedback entity) {
        checkIfFeedbackExists(entity.getUserId(), true);
        feedbackRepository.save(entity);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Transactional
    @Override
    public void updateFeedback(Long userId, FeedbackRequestResponse feedbackDto) {
        checkIfFeedbackExists(userId, false);
        feedbackRepository.updateFeedback(feedbackDto.comment(), feedbackDto.grade(), userId);
    }

    private void checkIfFeedbackExists(Long userId, boolean shouldNotExist) {
        boolean exists = feedbackRepository.existsByUserId(userId);
        if (shouldNotExist && exists) {
            throw new ThisFeedbackAlreadyExistException(userId.toString());
        } else if (!shouldNotExist && !exists) {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + userId);
        }

    }
}
