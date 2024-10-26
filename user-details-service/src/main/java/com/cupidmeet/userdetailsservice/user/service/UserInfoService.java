package com.cupidmeet.userdetailsservice.user.service;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserInfoRequest;

import java.util.UUID;

/**
 * Интерфейс, который предоставляет методы для управления информацией о пользователе,
 * включая обновление данных пользователя и управление прикреплёнными файлами.
 */
public interface UserInfoService {

    /**
     * Обновляет информацию о пользователе на основе предоставленных данных.
     *
     * @param userInfo Объект, содержащий новую информацию о пользователе.
     * @param userId   ID пользователя, чья информация должна быть обновлена.
     */
    void update(UserInfoRequest userInfo, UUID userId);
}