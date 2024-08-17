package com.cupidmeet.userdetailsservice.user.service.impl;

import com.cupidmeet.userdetailsservice.message.Messages;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserCreateRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserResponse;
import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.domain.types.Role;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
import com.cupidmeet.userdetailsservice.user.exception.EntityAlreadyExistsException;
import com.cupidmeet.userdetailsservice.user.exception.RoleAlreadyAssignedException;
import com.cupidmeet.userdetailsservice.user.exception.UnauthorizedException;
import com.cupidmeet.userdetailsservice.user.mapper.UserMapper;
import com.cupidmeet.userdetailsservice.user.repository.UserRepository;
import com.cupidmeet.userdetailsservice.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper converter;

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        log.info("Создание пользователя");
        User user = converter.toEntity(request);

        boolean exists = userRepository.existsByUserTelegramId(user.getUserTelegramId());
        if (exists) {
            throw new EntityAlreadyExistsException(
                    String.format(Messages.ALREADY_EXISTS_TELEGRAM.getTextPattern(), "Пользователь", user.getUserTelegramId())
            );
        }

        User created = userRepository.save(user);
        return get(created.getId());
    }

    @Override
    @Transactional
    public void deactivate(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", id)
            );
        }
        userRepository.updateUserStatus(id, Status.INACTIVE);
    }

    @Override
    @Transactional
    public void activate(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", id)
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
    @Transactional
    public void delete(UUID id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public void block(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", id)
            );
        }
        userRepository.updateUserStatus(id, Status.BANNED);
    }

    @Override
    public void assignRole(UUID adminId, UUID userId, Role role) {
        User user = getById(userId);
        User admin = getById(adminId);

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedException("Для назначения ролей требуются права администратора.");
        }

        if (user.getRole().equals(role)) {
            throw new RoleAlreadyAssignedException("У пользователя уже есть указанная роль.");
        }

        user.setRole(role);
        userRepository.saveAndFlush(user);
    }

    private User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(Messages.NOT_FOUND.getTextPattern(), "Пользователь", "идентификатором", id)
                ));
    }
}