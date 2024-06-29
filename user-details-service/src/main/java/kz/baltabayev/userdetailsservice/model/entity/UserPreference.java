package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents user preferences entity in the system.
 * This entity contains the preferences of a user, such as preferred gender and age range.
 * Extends the {@link BaseEntity} class to include common entity properties.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_preferences", schema = "user_detail")
@EqualsAndHashCode(callSuper = true)
public class UserPreference extends BaseEntity {

    /**
     * The unique identifier of the user preference.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The preferred gender of the user, represented by the {@link PreferredGender} enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_gender")
    private PreferredGender preferredGender;

    /**
     * The minimum age preferred by the user.
     */
    private Integer minAge;

    /**
     * The maximum age preferred by the user.
     */
    private Integer maxAge;

    /**
     * The user entity associated with this user preference.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Constructs a new UserPreference with the specified preferred gender, age range, and user.
     *
     * @param preferredGender The preferred gender of the user.
     * @param maxAge          The maximum age preferred by the user.
     * @param minAge          The minimum age preferred by the user.
     * @param user            The user entity associated with this preference.
     */
    public UserPreference(PreferredGender preferredGender, Integer maxAge, Integer minAge, User user) {
        this.preferredGender = preferredGender;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.user = user;
    }
}