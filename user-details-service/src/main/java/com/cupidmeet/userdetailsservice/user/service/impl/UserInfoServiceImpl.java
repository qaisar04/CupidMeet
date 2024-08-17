package com.cupidmeet.userdetailsservice.user.service.impl;

import com.cupidmeet.userdetailsservice.message.Messages;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;
import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.domain.entity.UserInfo;
import com.cupidmeet.userdetailsservice.user.mapper.UserInfoMapper;
import com.cupidmeet.userdetailsservice.user.repository.UserInfoRepository;
import com.cupidmeet.userdetailsservice.user.repository.UserRepository;
import com.cupidmeet.userdetailsservice.user.service.UserInfoService;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public void update(UserInfoRequest userInfo, UUID userId) {
        if (!userInfoRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException(
                    String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", userId)
            );
        }

        UserInfo existingInfo = getByUserId(userId);
        userInfoMapper.updateEntityFromDto(userInfo, existingInfo);
        userInfoRepository.save(existingInfo);
    }

    @Override
    @Transactional
    public void addAttachment(UUID userId, Set<String> fileIds) {
        UserInfo userInfo = getByUserId(userId);
        userInfo.getFileIds().addAll(fileIds);
        userInfoRepository.saveAndFlush(userInfo);
    }

    @Override
    @Transactional
    public void removeAttachment(UUID userId, Set<String> fileIds) {
        UserInfo userInfo = getByUserId(userId);
        userInfo.getFileIds().removeAll(fileIds);
        userInfoRepository.saveAndFlush(userInfo);
    }

    private UserInfo getByUserId(UUID id) {
        return userInfoRepository.getByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", id)
                ));
    }
}