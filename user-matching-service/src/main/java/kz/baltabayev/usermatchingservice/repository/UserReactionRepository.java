package kz.baltabayev.usermatchingservice.repository;

import kz.baltabayev.usermatchingservice.model.entity.UserReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for handling database operations for UserReaction entities.
 * Extends JpaRepository to provide standard CRUD operations and more.
 */
@Repository
public interface UserReactionRepository extends JpaRepository<UserReaction, Long> {

    /**
     * Finds a UserReaction by the user ID and the target user ID.
     * This method is useful for retrieving a specific reaction a user has towards another user.
     *
     * @param userId The ID of the user who reacted.
     * @param targetUserId The ID of the user who was the target of the reaction.
     * @return An Optional containing the UserReaction if found, or an empty Optional otherwise.
     */
    Optional<UserReaction> findByUserIdAndTargetUserId(Long userId, Long targetUserId);
}