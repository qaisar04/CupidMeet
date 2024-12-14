package com.cupidmeet.userdetailsservice.security.service.impl;

import com.cupidmeet.runtimecore.exception.CustomException;
import com.cupidmeet.userdetailsservice.security.service.KeycloakUserInfoExtractor;
import com.cupidmeet.userdetailsservice.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.cupidmeet.runtimecore.exception.CustomException.supplier;
import static com.cupidmeet.userdetailsservice.message.Messages.DOMAIN_USERNAME_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {

    @Override
    public String getDomainUsername() {
        try {
            return KeycloakUserInfoExtractor.getDomainUsername(SecurityContextHolder.getContext()
                            .getAuthentication())
                    .orElseThrow(supplier(DOMAIN_USERNAME_NOT_FOUND));
        } catch (CustomException e) {
           log.error("Ошибка при получении имени пользователя из контекста безопасности", e);
           return null;
        }
    }
}
