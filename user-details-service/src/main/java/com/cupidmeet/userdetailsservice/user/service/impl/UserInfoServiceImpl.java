package com.cupidmeet.userdetailsservice.user.service.impl;

import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.userdetailsservice.message.Messages;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;
import com.cupidmeet.userdetailsservice.user.domain.entity.UserInfo;
import com.cupidmeet.userdetailsservice.user.mapper.UserInfoMapper;
import com.cupidmeet.userdetailsservice.user.repository.UserInfoRepository;
import com.cupidmeet.userdetailsservice.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public void update(UserInfoRequest userInfo, UUID userId) {
        UserInfo existingInfo = getByUserId(userId);
        userInfoMapper.updateEntityFromDto(userInfo, existingInfo);
        userInfoRepository.save(existingInfo);
    }

    private UserInfo getByUserId(UUID id) {
        return userInfoRepository.getByUserId(id)
                .orElseThrow(CustomRuntimeException.supplier(
                        Messages.NOT_FOUND, "Пользователь", "идентификатором", id)
                );
    }
}