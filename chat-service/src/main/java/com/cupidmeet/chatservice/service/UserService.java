package com.cupidmeet.chatservice.service;

import org.keycloak.representations.AccessToken;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    /**
     * Получить токен пользователя.
     *
     * @return Токен
     */
    Optional<AccessToken> getToken();

    /**
     * Получить доменное имя пользователя.
     *
     * @return доменное имя
     */
    Optional<String> getDomainUsername();

    /**
     * Получить идентификатор текущего пользователя.
     *
     * @return идентификатор
     */
    Optional<UUID> getCurrentId();
}
