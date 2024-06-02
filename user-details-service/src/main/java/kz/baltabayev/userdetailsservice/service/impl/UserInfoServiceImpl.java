package kz.baltabayev.userdetailsservice.service.impl;

import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.repository.UserInfoRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserService userService;

    @Override
    public UserInfo create(UserInfo userInfo, Long userId) {
        User user = userService.get(userId);
        userInfo.setUser(user);
        return userInfoRepository.save(userInfo);
    }
}