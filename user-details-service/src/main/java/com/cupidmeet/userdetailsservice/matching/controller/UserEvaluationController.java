package com.cupidmeet.userdetailsservice.matching.controller;

import com.cupidmeet.userdetailsservice.matching.model.types.ReactionOutcome;
import com.cupidmeet.userdetailsservice.matching.model.types.ReactionType;
import com.cupidmeet.userdetailsservice.matching.service.UserEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserEvaluationController {

    private final UserEvaluationService userEvaluationService;

    @GetMapping("/evaluation/{userId}/rated-users")
    public ResponseEntity<List<UUID>> getRatedUserIds(@PathVariable("userId") UUID userId) {
        List<UUID> ratedUserIds = userEvaluationService.findRatedUserIds(userId);
        return ResponseEntity.ok(ratedUserIds);
    }

    @PostMapping("/reaction")
    public ResponseEntity<ReactionOutcome> submitReaction(
            @RequestParam("from") UUID fromUserId,
            @RequestParam("to") UUID toUserId,
            @RequestParam ReactionType reactionType
    ) {
        ReactionOutcome outcome = userEvaluationService.submitReaction(fromUserId, toUserId, reactionType);
        return ResponseEntity.ok(outcome);
    }
}