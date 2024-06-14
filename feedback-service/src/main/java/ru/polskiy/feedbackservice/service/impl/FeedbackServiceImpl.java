package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.exception.CreateComplaintException;
import ru.polskiy.feedbackservice.exception.CreateFeedbackException;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.model.repository.FeedbackRepository;
import ru.polskiy.feedbackservice.model.type.Status;
import ru.polskiy.feedbackservice.service.FeedbackService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final ComplaintRepository complaintRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    @Override
    public Complaint createComplaint(Complaint entity) {
        return Optional.of(entity)
                .map(complaint -> {
                    complaint.setStatus(Status.NEW);
                    return complaint;
                })
                .map(complaintRepository::save)
                .orElseThrow(() -> new CreateComplaintException(entity.toString()));
    }


    @Transactional
    public Feedback createFeedback(Feedback entity) {
        return Optional.of(entity)
                .map(feedbackRepository::save)
                .orElseThrow(() -> new CreateFeedbackException(entity.toString()));
    }

    @Override
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackRepository.findAll();
    }

}
