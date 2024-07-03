package kz.baltabayev.usermatchingservice.controller;

import kz.baltabayev.usermatchingservice.model.types.ReactionOutcome;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;
import kz.baltabayev.usermatchingservice.service.UserRatingService;
import kz.baltabayev.usermatchingservice.service.UserReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matching")
@RequiredArgsConstructor
public class UserMatchingController {

    private final UserReactionService userReactionService;
    private final UserRatingService userRatingService;

    @PostMapping("/submit")
    public ResponseEntity<ReactionOutcome> submitReaction(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam ReactionType reactionType
    ) {
        ReactionOutcome outcome = userReactionService.reactToUser(from, to, reactionType);
        return ResponseEntity.ok(outcome);
    }

    @GetMapping("/{userId}/rated-user-ids")
    public ResponseEntity<List<Long>> getRatedUserIds(@PathVariable("userId") Long userId) {
        List<Long> ratedUserIds = userRatingService.findRatedUserIds(userId);
        return ResponseEntity.ok(ratedUserIds);
    }
}