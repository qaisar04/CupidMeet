package com.cupidmeet.userdetailsservice.user.service;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserCreateRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserResponse;
import com.cupidmeet.userdetailsservice.user.domain.types.Role;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Интерфейс, который предоставляет методы для управления пользователями.
 */
public interface UserService {

    /**
     * Создает нового пользователя с указанными данными.
     *
     * @param request Объект, содержащий всю необходимую информацию для создания пользователя.
     * @return Ответ с информацией о созданном пользователе.
     */
    UserResponse create(UserCreateRequest request);

    /**
     * Деактивирует существующего пользователя.
     *
     * @param id Идентификатор пользователя.
     */
    void deactivate(UUID id);

    /**
     * Активирует существующего пользователя.
     *
     * @param id Идентификатор пользователя.
     */
    void activate(UUID id);

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с указанным идентификатором.
     */
    UserResponse get(UUID id);

    /**
     * Возвращает список пользователей по их идентификаторам.
     *
     * @param ids Идентификаторы пользователей.
     * @return Список пользователей с указанными идентификаторами.
     */
    Map<UUID, UserResponse> get(Collection<UUID> ids);

    /**
     * Удаляет пользователя с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которого нужно удалить.
     */
    void delete(UUID id);

    /**
     * Блокирует пользователя с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которого нужно заблокировать.
     */
    void block(UUID id);

    /**
     * Разблокирует пользователя с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которого нужно заблокировать.
     */
    void unblock(UUID id);

    /**
     * Назначает новую роль указанному пользователю.
     * Этот метод позволяет администратору изменить роль пользователя в системе.
     *
     * @param adminId Идентификатор администратора, выполняющего назначение роли.
     * @param userId  Идентификатор пользователя, чья роль должна быть изменена.
     * @param role    Новая роль, которая будет назначена пользователю.
     */
    void assignRole(UUID adminId, UUID userId, Role role);

    /**
     * Мягкое удаление пользователя с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которого нужно удалить.
     */
    void setSignDeleted(UUID id);
}