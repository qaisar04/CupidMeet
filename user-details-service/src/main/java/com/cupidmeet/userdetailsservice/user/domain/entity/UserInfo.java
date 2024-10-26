package com.cupidmeet.userdetailsservice.user.domain.entity;

import com.cupidmeet.userdetailsservice.user.domain.types.Gender;
import com.cupidmeet.userdetailsservice.user.domain.types.PersonalityType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Сущность, представляющая информацию о пользователе.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_infos")
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(callSuper = true, exclude = {"user"})
public class UserInfo extends BaseEntity {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Возраст пользователя.
     */
    private Integer age;

    /**
     * Город проживания пользователя.
     */
    private String city;

    /**
     * Пол пользователя.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Тип личности пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "personality_type", nullable = false)
    private PersonalityType personalityType;

    /**
     * Краткая биография пользователя.
     */
    private String bio;

    /**
     * Связанный с этим объектом пользователь.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}