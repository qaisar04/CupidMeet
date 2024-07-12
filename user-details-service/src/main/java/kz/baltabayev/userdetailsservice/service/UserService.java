package kz.baltabayev.userdetailsservice.service;

import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;

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

    void delete(Long id);
}