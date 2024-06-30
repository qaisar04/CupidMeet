package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;

/**
 * Service interface for managing user information.
 */
public interface UserInfoService {

    /**
     * Creates a new user information entry for the specified user ID.
     *
     * @param userInfo the user information to create
     * @param userId   the ID of the user
     * @return the created user information
     */
    UserInfo create(UserInfo userInfo, Long userId);

    /**
     * Updates the user information for the specified user ID.
     *
     * @param userInfo        the information of the user
     * @param userId          the ID of the user
     */
    void update(UserInfoRequest userInfo, Long userId);
}