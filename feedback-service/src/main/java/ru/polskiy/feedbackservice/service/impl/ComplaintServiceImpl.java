package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.exception.ComplaintToThisUserAlreadyExistException;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.service.ComplaintService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    @Transactional
    @Override
    public void createComplaint(Complaint entity) {
        if (complaintRepository.existsByFromUserIdAndToUserId(entity.getFromUserId(), entity.getToUserId())) {
            throw new ComplaintToThisUserAlreadyExistException(entity.toString());
        }
        if (Objects.equals(entity.getFromUserId(), entity.getToUserId())) {
            throw new IllegalArgumentException("Complaint mustn't be sent to yourself");
        }
        complaintRepository.save(entity);
    }

    @Override
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }
}
