package kz.baltabayev.usermatchingservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user's reaction to another user.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_reaction", schema = "matching_service")
public class UserReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "target_user_id")
    private Long targetUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type")
    private ReactionType reactionType;
}