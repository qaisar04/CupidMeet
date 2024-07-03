package kz.baltabayev.usermatchingservice.service;

import kz.baltabayev.usermatchingservice.model.types.ReactionOutcome;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;

public interface UserReactionService {

    ReactionOutcome reactToUser(Long userId, Long targetUserId, ReactionType reactionType);
}