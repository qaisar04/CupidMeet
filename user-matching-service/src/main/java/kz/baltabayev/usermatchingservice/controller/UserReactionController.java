package kz.baltabayev.usermatchingservice.controller;

import kz.baltabayev.usermatchingservice.model.types.ReactionOutcome;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;
import kz.baltabayev.usermatchingservice.service.UserReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user reactions.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class UserReactionController {

    private final UserReactionService userReactionService;

    /**
     * Submits a reaction from one user to another.
     *
     * @param fromUserId   the ID of the user submitting the reaction
     * @param toUserId     the ID of the user receiving the reaction
     * @param reactionType the type of reaction being submitted
     * @return a ResponseEntity containing the outcome of the reaction submission
     */
    @PostMapping
    public ResponseEntity<ReactionOutcome> submitReaction(
            @RequestParam("from") Long fromUserId,
            @RequestParam("to") Long toUserId,
            @RequestParam ReactionType reactionType
    ) {
        ReactionOutcome outcome = userReactionService.submitReaction(fromUserId, toUserId, reactionType);
        return ResponseEntity.ok(outcome);
    }
}