package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.types.Role;

/**
 * UserService interface.
 * This interface provides methods to manage users.
 */
public interface UserService {

    /**
     * Creates a new user with the specified details.
     *
     * @param user The user object containing all necessary information to create the user, including ID, username, additional user information, and preferences.
     * @throws EntityAlreadyExistsException if a user with the given ID already exists.
     */
    void create(User user);

    /**
     * Deactivates an existing user.
     *
     * @param id The ID of the user.
     */
    void deactivate(Long id);

    /**
     * Activates an existing user.
     *
     * @param id The ID of the user.
     */
    void activate(Long id);

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user.
     * @return The user with the given ID.
     */
    User get(Long id);

    /**
     * Deletes the user with the specified ID.
     *
     * @param id The ID of the user to delete.
     */
    void delete(Long id);

    /**
     * Blocks the user with the specified ID.
     *
     * @param id The ID of the user to block.
     */
    void block(Long id);

    /**
     * Assigns a new role to a specified user.
     * This method allows an administrator to change the role of a user in the system.
     *
     * @param adminId The ID of the administrator performing the role assignment.
     * @param userId  The ID of the user whose role is to be changed.
     * @param role    The new role to be assigned to the user.
     */
    void assignRole(Long adminId, Long userId, Role role);
}