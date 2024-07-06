package kz.baltabayev.usermatchingservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * Entity representing user evaluations.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_evaluation", schema = "matching_service")
public class UserEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "user_rated_list", joinColumns = @JoinColumn(name = "user_evaluation_id"))
    @Column(name = "rated_user_id")
    private List<Long> ratedUserIds = new LinkedList<>();

    /**
     * Constructor for creating a UserEvaluation with a specific user ID.
     *
     * @param userId the ID of the user being evaluated
     */
    public UserEvaluation(Long userId) {
        this.userId = userId;
    }
}