package com.cupidmeet.userdetailsservice.matching.controller;

import com.cupidmeet.userdetailsservice.matching.model.types.ReactionOutcome;
import com.cupidmeet.userdetailsservice.matching.model.types.ReactionType;
import com.cupidmeet.userdetailsservice.matching.service.UserEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserEvaluationController {

    private final UserEvaluationService userEvaluationService;

    /**
     * Получить список идентификаторов пользователей, которые были оценены.
     *
     * @param userId Идентификатор пользователя.
     * @return Список идентификаторов оцененных пользователей.
     */
    @Operation(operationId = "getRatedUserIds", summary = "Получить список идентификаторов пользователей, которые были оценены")
    @GetMapping("/evaluation/{userId}/rated-users")
    public ResponseEntity<Set<UUID>> getRatedUserIds(@PathVariable("userId") UUID userId) {
        Set<UUID> ratedUserIds = userEvaluationService.findRatedUserIds(userId);
        return ResponseEntity.ok(ratedUserIds);
    }

    /**
     * Оценить пользователя.
     *
     * @param fromUserId   Идентификатор пользователя, который оценивает.
     * @param toUserId     Идентификатор пользователя, которого оценивают.
     * @param reactionType Тип реакции (лайк, дизлайк и т.д.).
     * @param message  Сообщение отправленное вместе с лайком.
     * @return Результат реакции (например, совпадение).
     */
    @Operation(operationId = "submitReaction", summary = "Оценить пользователя")
    @PostMapping("/reaction")
    public ResponseEntity<ReactionOutcome> submitReaction(
            @RequestParam("from") UUID fromUserId,
            @RequestParam("to") UUID toUserId,
            @RequestParam ReactionType reactionType,
            @RequestBody(required = false) String message
    ) {
        ReactionOutcome outcome = userEvaluationService.submitReaction(fromUserId, toUserId, reactionType, message);
        return ResponseEntity.ok(outcome);
    }
}