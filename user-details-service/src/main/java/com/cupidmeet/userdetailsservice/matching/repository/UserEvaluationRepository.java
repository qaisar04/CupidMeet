package com.cupidmeet.userdetailsservice.matching.repository;

import com.cupidmeet.userdetailsservice.matching.model.entity.UserEvaluation;
import com.cupidmeet.userdetailsservice.matching.model.types.EvaluationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEvaluationRepository extends JpaRepository<UserEvaluation, UUID> {

    List<UserEvaluation> findByUserIdAndStatus(UUID userId, EvaluationStatus status);

    Optional<UserEvaluation> findByUserIdAndRatedUserId(UUID userId, UUID ratedUserId);

    List<UserEvaluation> findByEvaluationAtBeforeAndStatus(Instant evaluationAt, EvaluationStatus status);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE UserEvaluation ue SET ue.status = :status WHERE ue.id = :id")
    void updateEvaluationStatus(UUID id, EvaluationStatus status);
}