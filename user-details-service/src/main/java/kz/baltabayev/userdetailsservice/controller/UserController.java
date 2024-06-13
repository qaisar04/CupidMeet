package kz.baltabayev.userdetailsservice.controller;

import kz.baltabayev.userdetailsservice.mapper.UserMapper;
import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

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
        userService.deactivate(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(
            @PathVariable Long userId
    ) {
        User user = userService.get(userId);
        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(response);
    }
}