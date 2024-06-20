package ru.polskiy.feedbackservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.polskiy.feedbackservice.exception.ComplaintToThisUserAlreadyExistException;
import ru.polskiy.feedbackservice.exception.CreateComplaintException;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.repository.ComplaintRepository;
import ru.polskiy.feedbackservice.service.ComplaintService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    @Transactional
    @Override
    public Complaint createComplaint(Complaint entity) {
        if (complaintRepository.findByFromUserIdAndToUserId(entity.getFromUserId(), entity.getToUserId()) != null) {
            throw new ComplaintToThisUserAlreadyExistException(entity.toString());
        }
        return Optional.of(entity)
                .map(complaintRepository::save)
                .orElseThrow(() -> new CreateComplaintException(entity.toString()));
    }

    @Override
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }
}
