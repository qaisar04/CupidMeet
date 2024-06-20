package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.exception.*;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.model.repository.FeedbackRepository;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Transactional
    public Feedback createFeedback(Feedback entity) {
        if (feedbackRepository.findByUserId(entity.getUserId()) != null) {
            throw new ThisFeedbackAlreadyExistException(entity.toString());
        }
        return Optional.of(entity)
                .map(feedbackRepository::save)
                .orElseThrow(() -> new CreateFeedbackException(entity.toString()));
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}
