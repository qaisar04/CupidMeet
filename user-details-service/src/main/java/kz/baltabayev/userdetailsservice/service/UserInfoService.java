package kz.baltabayev.userdetailsservice.service;

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
     * @param name            the name of the user
     * @param age             the age of the user
     * @param city            the city of the user
     * @param gender          the gender of the user
     * @param personalityType the personalityType of the user
     * @param bio             the bio of the user
     * @param userId          the ID of the user
     */
    void update(String name, Integer age, String city, String gender, String personalityType, String bio, Long userId);
}