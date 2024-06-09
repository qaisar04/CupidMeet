package kz.baltabayev.userdetailsservice.service.impl;

import kz.baltabayev.userdetailsservice.client.StorageServiceClient;
import kz.baltabayev.userdetailsservice.model.dto.FileUploadResponse;
import kz.baltabayev.userdetailsservice.model.entity.FileAttachment;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.repository.FileAttachmentRepository;
import kz.baltabayev.userdetailsservice.repository.UserInfoRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserService userService;
    private final StorageServiceClient storageServiceClient;
    private final FileAttachmentRepository fileAttachmentRepository;

    @Value("${aws.s3.bucket.content-bucket}")
    private String USER_CONTENT_SOURCE;

    @Override
    public UserInfo create(UserInfo userInfo, Long userId) {
        User user = userService.get(userId);
        userInfo.setUser(user);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void update(UserInfo userInfo, Long userId) {
        User user = userService.get(userId);
        UserInfo existingUserInfo = user.getUserInfo();
        userInfo.setId(existingUserInfo.getId());
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void uploadAvatar(Long userId, List<MultipartFile> multipartFiles) {
        User user = userService.get(userId);
        FileUploadResponse[] responses = storageServiceClient.upload(USER_CONTENT_SOURCE, String.valueOf(user.getId()), multipartFiles).getBody();
        UserInfo userInfo = user.getUserInfo();
        Set<FileAttachment> fileAttachments = Arrays.stream(Objects.requireNonNull(responses))
                .map(r -> new FileAttachment(r.id(), r.source(), r.url(), userInfo))
                .map(fileAttachmentRepository::save)
                .collect(Collectors.toSet());
        userInfo.setFiles(fileAttachments);
        userInfoRepository.saveAndFlush(userInfo);
    }

    public void deleteAvatar(Long avatarId) {
        storageServiceClient.delete(USER_CONTENT_SOURCE, String.valueOf(avatarId));
    }
}