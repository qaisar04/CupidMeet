package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.model.entity.User;

/**
 * UserService interface.
 * This interface provides methods to manage users.
 */
public interface UserService {

    /**
     * Creates a new user.
     * @param id The ID of the user.
     * @param username The username of the user.
     */
    void create(Long id, String username);

    /**
     * Deactivates an existing user.
     * @param id The ID of the user.
     */
    void deactivate(Long id);

    /**
     * Activates an existing user.
     * @param id The ID of the user.
     */
    void activate(Long id);

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user.
     * @return The user with the given ID.
     */
    User get(Long id);

    void delete(Long id);

    void ban(Long id);
}