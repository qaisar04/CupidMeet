package kz.baltabayev.userdetailsservice.repository;

import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.types.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Inserts a new user into the database.
     *
     * @param id       The ID of the user to insert.
     * @param username The username of the user to insert.
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_detail.users (id, username, created_at, updated_at, status) VALUES (:id, :username, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')", nativeQuery = true)
    void insertUser(Long id, String username);

    /**
     * Updates the status of a user.
     *
     * @param id     The ID of the user whose status is being updated.
     * @param status The new status of the user.
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status, u.updatedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void updateUserStatus(Long id, Status status);
}