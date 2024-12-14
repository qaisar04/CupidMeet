package com.cupidmeet.userdetailsservice.security.service;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public final class KeycloakUserInfoExtractor {

    private KeycloakUserInfoExtractor() {
    }

    /**
     * Получить токен доступа Keycloak из SecurityContext.
     *
     * @return токен доступа Keycloak.
     */
    public static Optional<AccessToken> getKeycloakAccessToken(Authentication authentication) {
        return Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .filter(KeycloakPrincipal.class::isInstance)
                .map(KeycloakPrincipal.class::cast)
                .map(KeycloakPrincipal::getKeycloakSecurityContext)
                .map(KeycloakSecurityContext::getToken);
    }

    /**
     * Получить доменное имя пользователя из SecurityContext.
     *
     * @return доменное имя пользователя.
     */
    public static Optional<String> getDomainUsername(Authentication authentication) {
        return getKeycloakAccessToken(authentication)
                .map(IDToken::getPreferredUsername)
                .map(name -> name.split("\\\\", 2))
                .filter(segments -> segments.length > 1)
                .map(segments -> segments[segments.length - 1])
                .map(String::toUpperCase)
                .filter(name -> !name.isBlank());
    }
}
