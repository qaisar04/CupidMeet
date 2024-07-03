package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.dto.UserMatchResponse;

import java.util.List;
import java.util.Set;

/**
 * UserPreferenceService interface.
 * This interface provides methods to manage user preferences.
 */
public interface UserPreferenceService {

    /**
     * Creates a new user preference.
     * @param userId The ID of the user.
     * @param gender The gender preference of the user.
     */
    void create(Long userId, String gender);

    /**
     * Updates an existing user preference.
     * @param userId The ID of the user.
     * @param gender The gender preference of the user.
     * @param maxAge The maximum age preference of the user.
     * @param minAge The minimum age preference of the user.
     */
    void update(Long userId, String gender, Integer maxAge, Integer minAge);

    List<UserMatchResponse> findMatchingUsers(Long userId, Set<Long> excludedUserIds);
}