package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import kz.baltabayev.userdetailsservice.repository.UserInfoRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserInfoService interface providing operations for managing user information.
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserService userService;

    /** Error message for user info not found */
    public static final String NOT_FOUND_MESSAGE = "Not found userInfo for the user with id: ";

    /** Error message for user info already exists */
    public static final String ALREADY_EXISTS_MESSAGE = "UserInfo already exists for user with id: ";

    /**
     * Creates user information for the specified user ID.
     *
     * @param userInfo The UserInfo object containing user information to create
     * @param userId   The ID of the user for whom the information is being created
     * @return The created UserInfo object
     * @throws EntityAlreadyExistsException if user information already exists for the given user ID
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    @Override
    @Transactional
    public UserInfo create(UserInfo userInfo, Long userId) {
        if (userInfoRepository.existsByUserId(userId)) {
            throw new EntityAlreadyExistsException(ALREADY_EXISTS_MESSAGE + userId);
        }

        User user = userService.get(userId);
        userInfo.setUser(user);
        return userInfoRepository.save(userInfo);
    }

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
}