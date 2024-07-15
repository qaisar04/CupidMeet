package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import kz.baltabayev.userdetailsservice.repository.UserInfoRepository;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Implementation of the UserInfoService interface providing operations for managing user information.
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    /**
     * Error message for user info not found
     */
    public static final String NOT_FOUND_MESSAGE = "Not found userInfo for the user with id: ";

    /**
     * Error message for user info already exists
     */
    public static final String ALREADY_EXISTS_MESSAGE = "UserInfo already exists for user with id: ";

    /**
     * Updates user information for the specified user ID.
     *
     * @param userInfo The UserInfoRequest object containing updated user information
     * @param userId   The ID of the user for whom the information is being updated
     * @throws EntityNotFoundException if no user information exists for the given user ID
     */
    @Override
    @Transactional
    public void update(UserInfoRequest userInfo, Long userId) {
        if (!userInfoRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + userId);
        }
        userInfoRepository.updateUserInfoByUserId(
                userInfo.name(), userInfo.age(), userInfo.city(), Gender.valueOf(userInfo.gender()),
                PersonalityType.valueOf(userInfo.personalityType()), userInfo.bio(), userId
        );
    }

    /**
     * Adds attachments to the user information for the specified user ID.
     *
     * @param userId  The ID of the user
     * @param fileIds The IDs of the files to add as attachments
     */
    @Override
    @Transactional
    public void addAttachment(Long userId, Set<String> fileIds) {
        if (!userInfoRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + userId);
        }
        UserInfo userInfo = userInfoRepository.getByUserId(userId).get();
        userInfo.getFileIds().addAll(fileIds);
        userInfoRepository.saveAndFlush(userInfo);
    }

    /**
     * Removes attachments from the user information for the specified user ID.
     *
     * @param userId  The ID of the user
     * @param fileIds The IDs of the files to remove as attachments
     */
    @Override
    @Transactional
    public void removeAttachment(Long userId, Set<String> fileIds) {
        if (!userInfoRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException(NOT_FOUND_MESSAGE + userId);
        }
        UserInfo userInfo = userInfoRepository.getByUserId(userId).get();
        Set<String> currentFileIds = userInfo.getFileIds();
        currentFileIds.removeAll(fileIds);
        userInfoRepository.saveAndFlush(userInfo);
    }
}