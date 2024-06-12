package kz.baltabayev.userdetailsservice.controller;

import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/preference")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @PostMapping("{userId}/create")
    public ResponseEntity<Void> create(
            @RequestParam("gender") String gender,
            @PathVariable("userId") Long userId
    ) {
        userPreferenceService.create(userId, gender);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{userId}/update")
    public ResponseEntity<Void> update(
            @PathVariable("userId") Long userId,
            @RequestParam("gender") String gender,
            @RequestParam(value = "minAge") Integer minAge,
            @RequestParam(value = "minAge") Integer maxAge
    ) {
        userPreferenceService.update(userId, gender, minAge, maxAge);
        return ResponseEntity.ok().build();
    }
}