package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.exception.*;
import ru.polskiy.feedbackservice.model.entity.Feedback;
import ru.polskiy.feedbackservice.model.repository.FeedbackRepository;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Transactional
    @Override
    public void createFeedback(Feedback entity) {
        if (feedbackRepository.existsByUserId(entity.getUserId())) {
            throw new ThisFeedbackAlreadyExistException(entity.toString());
        }
        feedbackRepository.save(entity);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Transactional
    @Override
    public void updateFeedback(Feedback feedback) {
        if (!feedbackRepository.existsByUserId(feedback.getUserId())) {
            throw new NoSuchFeedbackException(feedback.toString());
        }
        Feedback feedbackToUpdate = feedbackRepository.findByUserId(feedback.getUserId());
        feedbackRepository.updateFeedback(feedback.getComment(),feedback.getGrade(),feedbackToUpdate.getId());
    }
}
