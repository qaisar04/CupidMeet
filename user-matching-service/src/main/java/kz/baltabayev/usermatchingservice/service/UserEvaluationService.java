package kz.baltabayev.usermatchingservice.service;

import java.util.List;

/**
 * Implementation of the UserEvaluationService interface.
 */
public interface UserEvaluationService {

    /**
     * Adds a target user ID to the list of rated users for a given user.
     *
     * @param userId       The ID of the user who is rating.
     * @param targetUserId The ID of the user being rated.
     */
    void addRatedId(Long userId, Long targetUserId);

    /**
     * Finds the list of user IDs that have been rated by a given user.
     *
     * @param userId The ID of the user whose ratings are being queried.
     * @return A list of user IDs that have been rated by the user.
     */
    List<Long> findRatedUserIds(Long userId);

    /**
     * Placeholder method to create a user evaluation record.
     * Implementation details would depend on specific requirements.
     *
     * @param userId The ID of the user for whom the evaluation record is to be created.
     */
    void create(Long userId);
}