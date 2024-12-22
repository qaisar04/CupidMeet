package com.cupidmeet.userdetailsservice.user.service.impl;

import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.userdetailsservice.message.Messages;
import com.cupidmeet.userdetailsservice.security.service.SecurityUserService;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserCreateRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserResponse;
import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.domain.types.Role;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
import com.cupidmeet.userdetailsservice.user.mapper.UserMapper;
import com.cupidmeet.userdetailsservice.user.repository.UserRepository;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cupidmeet.userdetailsservice.message.Messages.ROLE_ALREADY_ASSIGNED;
import static com.cupidmeet.userdetailsservice.message.Messages.UNAUTHORIZED_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper converter;
    private final SecurityUserService securityUserService;

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        log.info("Создание пользователя");
        User user = converter.toEntity(request);
        user.setUsername(securityUserService.getDomainUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomRuntimeException(
                    Messages.ALREADY_EXISTS, "Пользователь", user.getUsername() + "username"
            );
        }
        User created = userRepository.save(user);
        return get(created.getId());
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new CustomRuntimeException(
                    Messages.NOT_FOUND, "Пользователь", "идентификатором", id
            );
        }
        userRepository.updateUserStatus(id, Status.INACTIVE);
    }

    @Override
    @Transactional
    public void activate(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new CustomRuntimeException(
                    Messages.NOT_FOUND, "Пользователь", "идентификатором", id
            );
        }
        userRepository.updateUserStatus(id, Status.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse get(UUID id) {
        log.info("Запрос на получение информации о пользователя");
        User user = getById(id);
        return converter.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<UUID, UserResponse> get(Collection<UUID> ids) {
        return userRepository.findAllByIdIn(ids).stream()
                .collect(Collectors.toMap(User::getId, converter::toResponse));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public void block(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new CustomRuntimeException(
                    Messages.NOT_FOUND, "Пользователь", "идентификатором", id
            );
        }
        userRepository.updateUserStatus(id, Status.BANNED);
    }

    @Override
    public void unblock(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new CustomRuntimeException(
                    Messages.NOT_FOUND, "Пользователь", "идентификатором", id
            );
        }
        userRepository.updateUserStatus(id, Status.ACTIVE);
    }

    @Override
    public void assignRole(UUID adminId, UUID userId, Role role) {
        User user = getById(userId);
        User admin = getById(adminId);

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new CustomRuntimeException(UNAUTHORIZED_ERROR);
        }

        if (user.getRole().equals(role)) {
            throw new CustomRuntimeException(ROLE_ALREADY_ASSIGNED);
        }

        user.setRole(role);
        userRepository.saveAndFlush(user);
    }

    private User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(
                        Messages.NOT_FOUND, "Пользователь", "идентификатором", id)
                );
    }

    @Override
    @Transactional
    public void setSignDeleted(UUID id) {
        User user = getById(id);
        user.setSignDeleted(true);
        userRepository.save(user);
    }
}