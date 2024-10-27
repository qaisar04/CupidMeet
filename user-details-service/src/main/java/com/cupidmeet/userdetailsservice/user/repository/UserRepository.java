package com.cupidmeet.userdetailsservice.user.repository;

import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Обновляет статус пользователя и время обновления.
     *
     * @param id     идентификатор пользователя
     * @param status новый статус пользователя
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status, u.updatedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void updateUserStatus(UUID id, Status status);

    /**
     * Проверяет, существует ли пользователь с заданным именем пользователя.
     *
     * @param username имя пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);

    /**
     * Находит всех пользователей по списку идентификаторов.
     *
     * @param ids список идентификаторов пользователей
     * @return список пользователей
     */
    List<User> findAllByIdIn(Collection<UUID> ids);
}