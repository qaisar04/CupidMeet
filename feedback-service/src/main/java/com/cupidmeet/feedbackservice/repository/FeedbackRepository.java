package com.cupidmeet.feedbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.cupidmeet.feedbackservice.domain.entity.Feedback;
import com.cupidmeet.feedbackservice.domain.type.Grade;

import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    Optional<Feedback> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    boolean existsById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Feedback u SET u.comment = :comment,u.grade= :grade WHERE u.id = :id")
    void updateFeedback(String comment, Grade grade, Long id);
}