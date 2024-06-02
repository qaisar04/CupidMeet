package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_infos")
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Integer age;

    private String city;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "personality_type", nullable = false)
    private PersonalityType personalityType;

    private String bio;

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FileAttachment> files;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}