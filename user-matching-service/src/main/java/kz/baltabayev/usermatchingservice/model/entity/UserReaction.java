package kz.baltabayev.usermatchingservice.model.entity;

import jakarta.persistence.*;
import kz.baltabayev.usermatchingservice.model.types.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_reaction") // schema = "user-matching-service"
public class UserReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "target_user_id", nullable = false)
    private Long targetUserId;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}