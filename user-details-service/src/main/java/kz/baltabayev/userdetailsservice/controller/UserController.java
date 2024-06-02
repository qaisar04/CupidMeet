package kz.baltabayev.userdetailsservice.controller;

import kz.baltabayev.userdetailsservice.mapper.UserInfoMapper;
import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoCreateRequest;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

    @PostMapping("/create")
    public ResponseEntity<Void> create(
            @RequestBody UserCreateRequest request
    ) {
        userService.create(request.id(), request.username());
        return ResponseEntity.ok().build();
    }

    @PostMapping("{userId}/info/create")
    public ResponseEntity<Void> create(
            @RequestBody UserInfoCreateRequest request,
            @PathVariable("userId") Long userId
    ) {
        UserInfo userInfo = userInfoMapper.toEntity(request);
        userInfoService.create(userInfo, userId);
        return ResponseEntity.ok().build();
    }
}