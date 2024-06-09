package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInfoService {

    UserInfo create(UserInfo userInfo, Long userId);

    void update(UserInfo userInfo, Long userId);

    void uploadAvatar(Long userId, List<MultipartFile> multipartFiles);

    void deleteAvatar(Long avatarId);
}