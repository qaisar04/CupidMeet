package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public Optional<AccessToken> getToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(v -> v instanceof KeycloakAuthenticationToken ? (KeycloakAuthenticationToken)v : null)
                .map(AbstractAuthenticationToken::getDetails)
                .map(v -> v instanceof SimpleKeycloakAccount ? (SimpleKeycloakAccount)v : null)
                .map(SimpleKeycloakAccount::getKeycloakSecurityContext)
                .map(RefreshableKeycloakSecurityContext::getToken);
    }

    @Override
    public Optional<String> getDomainUsername() {
        return getToken().flatMap(this::getUsernameByToken);
    }

    @Override
    public Optional<UUID> getCurrentId() {
        return getToken().flatMap(token -> Optional.of(UUID.fromString(token.getSubject())));
    }

    private Optional<String> getUsernameByToken(AccessToken accessToken) {
        return Optional.ofNullable(accessToken)
                .map(IDToken::getPreferredUsername)
                .map(name -> name.split("\\\\", 2))
                .map(segments -> segments[segments.length - 1])
                .map(String::toUpperCase)
                .filter(name -> !name.isBlank());
    }
}
