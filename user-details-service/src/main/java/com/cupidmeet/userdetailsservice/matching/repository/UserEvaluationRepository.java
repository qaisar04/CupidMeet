package com.cupidmeet.userdetailsservice.matching.repository;

import com.cupidmeet.userdetailsservice.matching.model.entity.UserEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEvaluationRepository extends JpaRepository<UserEvaluation, UUID> {

    List<UserEvaluation> findByUserId(UUID userId);

    Optional<UserEvaluation> findByUserIdAndRatedUserId(UUID userId, UUID ratedUserId);
}