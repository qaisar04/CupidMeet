package com.cupidmeet.userdetailsservice.user.domain.entity;

import jakarta.persistence.*;
import com.cupidmeet.userdetailsservice.user.domain.types.Gender;
import com.cupidmeet.userdetailsservice.user.domain.types.PersonalityType;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
     * Набор идентификаторов файлов, связанных с пользователем.
     */
    @ElementCollection
    @CollectionTable(name = "user_info_file_ids", joinColumns = @JoinColumn(name = "user_info_id"))
    @Column(name = "file_ids")
    private Set<String> fileIds = new HashSet<>();

    /**
     * Связанный с этим объектом пользователь.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}