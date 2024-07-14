package kz.baltabayev.userdetailsservice.repository;

import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing UserPreference entities in the database.
 */
@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

    /**
     * Checks if UserPreference exists for a given user ID.
     *
     * @param userId The ID of the user to check for existence of UserPreference.
     * @return True if UserPreference exists, false otherwise.
     */
    boolean existsByUserId(Long userId);

    /**
     * Retrieves UserPreference by user ID.
     *
     * @param userId The ID of the user whose UserPreference is being retrieved.
     * @return An Optional containing the UserPreference if found, empty otherwise.
     */
    Optional<UserPreference> findByUserId(Long userId);
}