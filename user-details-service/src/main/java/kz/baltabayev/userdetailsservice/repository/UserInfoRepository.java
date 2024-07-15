package kz.baltabayev.userdetailsservice.repository;

import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.types.Gender;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing UserInfo entities in the database.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {

    /**
     * Updates UserInfo attributes by user ID.
     *
     * @param name           The updated name of the user.
     * @param age            The updated age of the user.
     * @param city           The updated city of residence of the user.
     * @param gender         The updated gender of the user.
     * @param personalityType The updated personality type of the user.
     * @param bio            The updated biography of the user.
     * @param userId         The ID of the user whose information is being updated.
     */
    @Modifying
    @Transactional
    @Query(
            "UPDATE UserInfo u SET " +
            "u.name = :name," +
            "u.age = :age," +
            "u.city = :city, " +
            "u.gender = :gender," +
            "u.personalityType = :personalityType," +
            "u.bio = :bio, " +
            "u.updatedAt = CURRENT_TIMESTAMP " +
            "WHERE u.user.id = :userId"
    )
    void updateUserInfoByUserId(
            String name, Integer age, String city, Gender gender,
            PersonalityType personalityType, String bio, Long userId
    );

    /**
     * Checks if UserInfo exists for a given user ID.
     *
     * @param userId The ID of the user to check for existence of UserInfo.
     * @return True if UserInfo exists, false otherwise.
     */
    boolean existsByUserId(Long userId);

    /**
     * Retrieves user information from the database based on the provided userId.
     *
     * @param userId The unique identifier of the user whose information is to be retrieved.
     * @return An instance of {@link UserInfo} containing the details of the user identified by userId,
     *         or null if no such user exists.
     */
    Optional<UserInfo> getByUserId(Long userId);
}