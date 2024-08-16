package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents user information entity in the system.
 * This entity contains detailed information about a user, such as name, age, city, gender, and personality type.
 * Extends the {@link BaseEntity} class to include common entity properties.
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
     * The unique identifier of the user information.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The age of the user.
     */
    private Integer age;

    /**
     * The city where the user lives.
     */
    private String city;

    /**
     * The gender of the user, represented by the {@link Gender} enum.
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * The personality type of the user, represented by the {@link PersonalityType} enum.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "personality_type", nullable = false)
    private PersonalityType personalityType;

    /**
     * A brief biography of the user.
     */
    private String bio;

    /**
     * The set of file ids associated with this user information.
     */
    @ElementCollection
    @CollectionTable(name = "user_info_file_ids", joinColumns = @JoinColumn(name = "user_info_id"))
    @Column(name = "file_ids")
    private Set<String> fileIds = new HashSet<>();

    /**
     * The user entity associated with this user information.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}