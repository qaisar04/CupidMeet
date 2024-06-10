package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.dto.ComplaintFeedbackDto;
import ru.polskiy.feedbackservice.mapper.ComplaintMapper;
import ru.polskiy.feedbackservice.mapper.FeedbackMapper;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.model.repository.FeedbackRepository;
import ru.polskiy.feedbackservice.model.type.Status;
import ru.polskiy.feedbackservice.service.FeedBackService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedBackServiceImpl implements FeedBackService {
    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;


    @Transactional
    @Override
    public ComplaintFeedbackDto createComplaint(Complaint entity) {
        return Optional.of(entity)
                .map(complaint -> {
                    complaint.setStatus(Status.NEW);
                    return complaint;
                })
                .map(complaintRepository::save)
                .map(complaintMapper::toDto)
                .orElseThrow();
    }


    @Transactional
    public ComplaintFeedbackDto createFeedback(Feedback entity) {
        return Optional.of(entity)
                .map(feedbackRepository::save)
                .map(feedbackMapper::toDto)
                .orElseThrow();
    }

}
