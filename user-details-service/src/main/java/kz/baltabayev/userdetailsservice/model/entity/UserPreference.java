package kz.baltabayev.userdetailsservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_preferences")
@EqualsAndHashCode(callSuper = true)
public class UserPreference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_gender")
    private PreferredGender preferredGender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}