package kz.baltabayev.usermatchingservice.service;

import kz.baltabayev.usermatchingservice.model.types.ReactionOutcome;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;

/**
 * Interface for the UserReactionService.
 * Defines the contract for submitting reactions between users.
 */
public interface UserReactionService {

    /**
     * Submits a reaction from one user towards another.
     * This method is responsible for processing user reactions, such as likes or dislikes,
     * and determining the outcome of these reactions.
     *
     * @param userId The ID of the user submitting the reaction.
     * @param targetUserId The ID of the user who is the target of the reaction.
     * @param reactionType The type of reaction being submitted (e.g., LIKE, DISLIKE).
     * @return The outcome of the reaction submission, which could include creating a match,
     *         blocking a user, or no action.
     */
    ReactionOutcome submitReaction(Long userId, Long targetUserId, ReactionType reactionType);
}