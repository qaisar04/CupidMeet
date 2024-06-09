package kz.baltabayev.userdetailsservice.controller;

import kz.baltabayev.userdetailsservice.mapper.UserInfoMapper;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

    @PostMapping("{userId}/create")
    public ResponseEntity<Void> create(
            @RequestBody UserInfoRequest request,
            @PathVariable("userId") Long userId
    ) {
        UserInfo userInfo = userInfoMapper.toEntity(request);
        userInfoService.create(userInfo, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{userId}/update")
    public ResponseEntity<Void> update(
            @RequestBody UserInfoRequest request,
            @PathVariable("userId") Long userId
    ) {
        UserInfo userInfo = userInfoMapper.toEntity(request);
        userInfoService.update(userInfo, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{userId}/avatar")
    public ResponseEntity<Void> uploadAvatar(
            @RequestParam("file") List<MultipartFile> multipartFiles,
            @PathVariable Long userId) {
        userInfoService.uploadAvatar(userId, multipartFiles);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{avatarId}/avatar")
    public ResponseEntity<Void> deleteAvatar(
            @PathVariable Long avatarId
    ) {
        userInfoService.deleteAvatar(avatarId);
        return ResponseEntity.ok().build();
    }
}