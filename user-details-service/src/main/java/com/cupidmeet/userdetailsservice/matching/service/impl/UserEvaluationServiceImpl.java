package com.cupidmeet.userdetailsservice.matching.service.impl;

import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.userdetailsservice.matching.model.entity.UserEvaluation;
import com.cupidmeet.userdetailsservice.matching.model.types.EvaluationStatus;
import com.cupidmeet.userdetailsservice.matching.model.types.ReactionOutcome;
import com.cupidmeet.userdetailsservice.matching.model.types.ReactionType;
import com.cupidmeet.userdetailsservice.matching.repository.UserEvaluationRepository;
import com.cupidmeet.userdetailsservice.matching.service.UserEvaluationService;
import com.cupidmeet.userdetailsservice.message.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEvaluationServiceImpl implements UserEvaluationService {

    private final UserEvaluationRepository userEvaluationRepository;

    @Override
    public Set<UUID> findRatedUserIds(UUID userId) {
        List<UserEvaluation> evaluations = userEvaluationRepository.findByUserIdAndStatus(
                userId, EvaluationStatus.ACTIVE
        );
        return evaluations.stream()
                .map(UserEvaluation::getRatedUserId)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public ReactionOutcome submitReaction(UUID userId, UUID targetUserId, ReactionType reactionType, String message) {
        Optional<UserEvaluation> existingReaction = userEvaluationRepository
                .findByUserIdAndRatedUserId(userId, targetUserId);
        String text = reactionType == ReactionType.LIKE ? message : null;

        if (userId.equals(targetUserId)) {
            throw new CustomRuntimeException(Messages.SELF_REACTION_NOT_ALLOWED);
        }

        UserEvaluation userEvaluation;
        if (existingReaction.isPresent()) {
            userEvaluation = existingReaction.get();
            userEvaluation.setReactionType(reactionType);
            userEvaluation.setMessage(text);
        } else {
            userEvaluation = new UserEvaluation();
            userEvaluation.setUserId(userId);
            userEvaluation.setRatedUserId(targetUserId);
            userEvaluation.setReactionType(reactionType);
            userEvaluation.setStatus(EvaluationStatus.ACTIVE);
            userEvaluation.setMessage(text);
        }

        userEvaluationRepository.save(userEvaluation);

        if (reactionType == ReactionType.LIKE) {
            Optional<UserEvaluation> reciprocalReaction = userEvaluationRepository.findByUserIdAndRatedUserId(targetUserId, userId);
            if (reciprocalReaction.isPresent() && reciprocalReaction.get().getReactionType() == ReactionType.LIKE) {
                return ReactionOutcome.MUTUAL_LIKE;
            } else {
                return ReactionOutcome.WAIT_FOR_THE_RETURN_RESPONSE;
            }
        } else {
            return ReactionOutcome.NONE;
        }
    }
}