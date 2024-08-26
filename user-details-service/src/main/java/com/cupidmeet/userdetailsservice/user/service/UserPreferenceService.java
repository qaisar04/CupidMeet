package com.cupidmeet.userdetailsservice.user.service;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserMatchResponse;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserPreferenceRequest;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, который предоставляет методы для управления предпочтениями пользователей и поиска подходящих пользователей.
 */
public interface UserPreferenceService {

    /**
     * Обновляет предпочтения пользователя на основе предоставленных данных.
     *
     * @param userId               ID пользователя, чьи предпочтения нужно обновить.
     * @param userPreferenceRequest Объект, содержащий новые предпочтения пользователя.
     */
    void update(UUID userId, UserPreferenceRequest userPreferenceRequest);

    /**
     * Находит подходящих пользователей на основе предпочтений заданного пользователя.
     * Исключает пользователей с указанными ID.
     *
     * @param userId          ID пользователя, для которого выполняется поиск.
     * @return Список пользователей, подходящих по предпочтениям.
     */
    List<UserMatchResponse> findMatchingUsers(UUID userId);

    /**
     * Находит подходящих пользователей на основе предпочтений заданного пользователя. (Тестовый)
     * Исключает пользователей с указанными ID.
     *
     * @param userId          ID пользователя, для которого выполняется поиск.
     * @return Список пользователей, подходящих по предпочтениям.
     */
    List<UserMatchResponse> findMatchingUsersTest(UUID userId);
}