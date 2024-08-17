package com.cupidmeet.userdetailsservice.matching.service;

import com.cupidmeet.userdetailsservice.matching.model.types.ReactionOutcome;
import com.cupidmeet.userdetailsservice.matching.model.types.ReactionType;

import java.util.List;
import java.util.UUID;

public interface UserEvaluationService {

    List<UUID> findRatedUserIds(UUID userId);

    ReactionOutcome submitReaction(UUID userId, UUID targetUserId, ReactionType reactionType);
}