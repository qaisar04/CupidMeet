package com.cupidmeet.feedbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cupidmeet.feedbackservice.domain.entity.Complaint;

import java.util.UUID;


public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {

    boolean existsByFromUserIdAndToUserId(UUID fromUserId, UUID toUserId);
}