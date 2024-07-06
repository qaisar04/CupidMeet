package kz.baltabayev.usermatchingservice.controller;

import kz.baltabayev.usermatchingservice.service.UserEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user evaluations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/evaluation")
public class UserEvaluationController {

    private final UserEvaluationService userRatingService;

    /**
     * Retrieves the list of user IDs that the specified user has rated.
     *
     * @param userId the ID of the user whose rated user IDs are to be retrieved
     * @return a ResponseEntity containing the list of rated user IDs
     */
    @GetMapping("/{userId}/rated-users")
    public ResponseEntity<List<Long>> getRatedUserIds(@PathVariable("userId") Long userId) {
        List<Long> ratedUserIds = userRatingService.findRatedUserIds(userId);
        return ResponseEntity.ok(ratedUserIds);
    }

    /**
     * Creates a new user evaluation for the specified user.
     *
     * @param userId the ID of the user for whom the evaluation is to be created
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam Long userId
    ) {
        userRatingService.create(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}