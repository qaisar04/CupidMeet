package kz.baltabayev.usermatchingservice.repository;

import kz.baltabayev.usermatchingservice.model.entity.UserEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserEvaluationRepository interface for handling database operations related to UserEvaluation entities.
 * Extends JpaRepository to leverage Spring Data JPA functionalities.
 */
public interface UserEvaluationRepository extends JpaRepository<UserEvaluation, Long> {

    /**
     * Finds a UserEvaluation by the user's ID.
     *
     * @param userId The ID of the user for whom the evaluation is to be found.
     * @return An Optional containing the UserEvaluation if found, or an empty Optional otherwise.
     */
    Optional<UserEvaluation> findByUserId(Long userId);

    /**
     * Checks if a UserEvaluation exists for a given user ID.
     *
     * @param userId The ID of the user to check for an existing evaluation.
     * @return true if an evaluation exists for the user, false otherwise.
     */
    boolean existsByUserId(Long userId);
}