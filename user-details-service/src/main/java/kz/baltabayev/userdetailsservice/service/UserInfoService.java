package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;

import java.util.Set;

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
     * @param userInfo the information of the user
     * @param userId   the ID of the user
     */
    void update(UserInfoRequest userInfo, Long userId);

    /**
     * Adds attachments to the user information for the specified user ID.
     *
     * @param userId   the ID of the user
     * @param fileIds  the IDs of the files to add as attachments
     */
    void addAttachment(Long userId, Set<String> fileIds);

    /**
     * Removes attachments from the user information for the specified user ID.
     *
     * @param userId   the ID of the user
     * @param fileIds  the IDs of the files to remove as attachments
     */
    void removeAttachment(Long userId, Set<String> fileIds);
}