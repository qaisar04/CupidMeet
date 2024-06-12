package kz.baltabayev.userdetailsservice.controller;

import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(
            @RequestBody UserCreateRequest request
    ) {
        userService.create(request.id(), request.username());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> get(
            @PathVariable Long id
    ) {
        userService.deactivate(id);
        return ResponseEntity.ok().build();
    }
}