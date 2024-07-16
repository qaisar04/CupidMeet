package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.userdetailsservice.model.types.Role;
import kz.baltabayev.userdetailsservice.model.types.Status;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a user entity in the system.
 * This entity contains basic user information and references to user preferences and user information.
 * Extends the {@link BaseEntity} class to include common entity properties.
 * Uses Lombok annotations for boilerplate code reduction.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "user_detail")
@EqualsAndHashCode(callSuper = true, exclude = {"userPreference", "userInfo"})
@ToString(callSuper = true, exclude = {"userPreference", "userInfo"})
public class User extends BaseEntity {

    /**
     * The unique identifier of the user.
     */
    @Id
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The status of the user, represented by the {@link Status} enum.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * The role of the user, indicating their level of access within the system.
     * Represented by the {@link Role} enum, it determines the user's permissions.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The user's preferences, represented by the {@link UserPreference} entity.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPreference userPreference;

    /**
     * The user's additional information, represented by the {@link UserInfo} entity.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserInfo userInfo;

    /**
     * Constructs a new User with the specified id and username.
     *
     * @param id       The unique identifier of the user.
     * @param username The username of the user.
     */
    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}