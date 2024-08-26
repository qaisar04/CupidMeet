package com.cupidmeet.userdetailsservice.user.domain.entity;

import jakarta.persistence.*;
import com.cupidmeet.userdetailsservice.user.domain.types.Role;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
import lombok.*;

/**
 * Представляет сущность пользователя в системе.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true, exclude = {"userPreference", "userInfo"})
@ToString(callSuper = true, exclude = {"userPreference", "userInfo"})
public class User extends BaseEntity {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long userTelegramId;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Статус пользователя.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Роль пользователя, указывающая на его уровень доступа в системе.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Предпочтения пользователя.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPreference userPreference;

    /**
     * Дополнительная информация о пользователе.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserInfo userInfo;
}