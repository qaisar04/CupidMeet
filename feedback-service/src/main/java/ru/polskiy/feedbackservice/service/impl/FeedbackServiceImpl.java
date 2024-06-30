package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
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
        if (feedbackRepository.exists(Example.of(entity))
        ) {
            throw new ThisFeedbackAlreadyExistException(entity.toString());
        }
        if (entity.getGrade() < 1 || entity.getGrade() > 5) {
            throw new GradeOutOfBoundsException(entity.getGrade().toString());
        }
        try {
            feedbackRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateFeedbackException(entity.toString());
        }


    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Transactional
    @Override
    public Feedback patchFeedback(Feedback feedback) {
        Optional<Feedback> dbFeedback = Optional.ofNullable(feedbackRepository.findByUserId(feedback.getUserId()));
        if (dbFeedback.isEmpty()) {
            throw new NoSuchFeedbackException(feedback.toString());
        }
        Feedback feedbackToPatch = dbFeedback.get();
        return Optional.of(feedbackToPatch)
                .map(IT -> {
                    IT.setComment(feedback.getComment());
                    return IT;
                })
                .map(feedbackRepository::save)
                .orElseThrow(() -> new CreateFeedbackException(feedback.toString()));
    }
}
