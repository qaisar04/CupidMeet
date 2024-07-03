package kz.baltabayev.usermatchingservice.service.impl;

import kz.baltabayev.usermatchingservice.model.entity.UserReaction;
import kz.baltabayev.usermatchingservice.model.types.ReactionOutcome;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;
import kz.baltabayev.usermatchingservice.repository.UserReactionRepository;
import kz.baltabayev.usermatchingservice.service.UserRatingService;
import kz.baltabayev.usermatchingservice.service.UserReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReactionServiceImpl implements UserReactionService {

    private final UserReactionRepository userReactionRepository;
    private final UserRatingService userRatingService;

    @Override
    @Transactional
    public ReactionOutcome reactToUser(Long userId, Long targetUserId, ReactionType reactionType) {
        Optional<UserReaction> existingReaction = userReactionRepository.findByUserIdAndTargetUserId(userId, targetUserId);

        UserReaction userReaction;
        if (existingReaction.isPresent()) {
            userReaction = existingReaction.get();
            userReaction.setReactionType(reactionType);
        } else {
            userReaction = new UserReaction();
            userReaction.setUserId(userId);
            userReaction.setTargetUserId(targetUserId);
            userReaction.setReactionType(reactionType);
        }

        userRatingService.addRatedId(userId, targetUserId);
        userReactionRepository.save(userReaction);

        if (reactionType == ReactionType.LIKE) {
            Optional<UserReaction> reciprocalReaction = userReactionRepository.findByUserIdAndTargetUserId(targetUserId, userId);
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