package com.cupidmeet.feedbackservice.service.impl;

import com.cupidmeet.commonmessage.exception.CommonRuntimeException;
import com.cupidmeet.feedbackservice.domain.dto.ComplaintCreateRequest;
import com.cupidmeet.feedbackservice.domain.dto.ComplaintResponse;
import com.cupidmeet.feedbackservice.domain.entity.Complaint;
import com.cupidmeet.feedbackservice.mapper.ComplaintMapper;
import com.cupidmeet.feedbackservice.repository.ComplaintRepository;
import com.cupidmeet.feedbackservice.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.cupidmeet.feedbackservice.message.Messages.*;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintMapper complaintMapper;

    @Transactional
    @Override
    public void create(ComplaintCreateRequest request) {
        Complaint complaint = complaintMapper.toEntity(request);
        if (complaintRepository.existsByFromUserIdAndToUserId(complaint.getFromUserId(), complaint.getToUserId())) {
            throw new CommonRuntimeException(COMPLAINT_ALREADY_EXIST, complaint.getFromUserId(), complaint.getToUserId());
        }

        if (Objects.equals(complaint.getFromUserId(), complaint.getToUserId())) {
            throw new CommonRuntimeException(COMPLAINT_SELF_NOT_ALLOWED);
        }

        complaintRepository.save(complaint);
    }

    @Override
    public List<ComplaintResponse> get() {
        return complaintRepository.findAll().stream()
                .map(complaintMapper::toResponse).toList();
    }

    @Override
    public ComplaintResponse get(UUID id) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(id);
        if (optionalComplaint.isEmpty()) {
            throw new CommonRuntimeException(NOT_FOUND, "Жалоба", "идентификатором", id);
        }

        return complaintMapper.toResponse(optionalComplaint.get());
    }
}