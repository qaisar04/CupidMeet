package kz.baltabayev.userdetailsservice.controller;

import kz.baltabayev.userdetailsservice.mapper.FileAttachmentMapper;
import kz.baltabayev.userdetailsservice.mapper.UserInfoMapper;
import kz.baltabayev.userdetailsservice.model.dto.FileAttachmentRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.service.FileAttachmentService;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;
    private final FileAttachmentMapper fileAttachmentMapper;
    private final FileAttachmentService fileAttachmentService;

    @PostMapping("{userId}/create")
    public ResponseEntity<Void> create(
            @RequestBody UserInfoRequest request,
            @PathVariable("userId") Long userId
    ) {
        UserInfo userInfo = userInfoMapper.toEntity(request);
        userInfoService.create(userInfo, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> addAttachment(Long userId, FileAttachmentRequest request) {
        FileAttachment fileAttachment = fileAttachmentMapper.toEntity(request);
        fileAttachmentService.addAttachment(userId, fileAttachment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAvatar(
            @PathVariable UUID attachmentId
    ) {
        fileAttachmentService.removeAttachment(attachmentId);
        return ResponseEntity.ok().build();
    }
}