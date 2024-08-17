package com.cupidmeet.userdetailsservice.user.domain.entity;

import jakarta.persistence.*;
import com.cupidmeet.userdetailsservice.user.domain.types.PreferredGender;
import lombok.*;

/**
 * Сущность, представляющая предпочтения пользователя.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_preferences")
@EqualsAndHashCode(callSuper = true, exclude = "user")
@ToString(callSuper = true, exclude = "user")
public class UserPreference extends BaseEntity {

    /**
     * Предпочтительный пол пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_gender")
    private PreferredGender preferredGender;

    /**
     * Минимальный предпочитаемый возраст.
     */
    private Integer minAge;

    /**
     * Максимальный предпочитаемый возраст.
     */
    private Integer maxAge;

    /**
     * Пользователь, связанный с этими предпочтениями.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}