package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.types.Status;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserService interface providing operations for managing user entities.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
    private final UserPreferenceService userPreferenceService;

    /**
     * Error message for user not found
     */
    public static final String NOT_FOUND_USER_MESSAGE = "Not found user with id: ";

    /**
     * Error message for user already exists
     */
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists with id: ";

    /**
     * Creates a new user with the specified details.
     * <p>
     * This method performs the following steps:
     * <ol>
     *     <li>Checks if a user with the given ID already exists.</li>
     *     <li>Inserts a new user record.</li>
     *     <li>Creates user information.</li>
     *     <li>Creates user preferences.</li>
     * </ol>
     * <p>
     * This method is transactional, ensuring atomicity.
     *
     * @param user The user object containing all necessary information to create the user, including ID, username, additional user information, and preferences.
     * @throws EntityAlreadyExistsException if a user with the given ID already exists.
     */
    @Override
    @Transactional
    public void create(User user) {
        checkIfUserExists(user.getId(), true);
        userRepository.insertUser(user.getId(), user.getUsername());
        userInfoService.create(user.getUserInfo(), user.getId());
        userPreferenceService.create(user.getId(), user.getUserPreference().getPreferredGender().name());
    }

    /**
     * Deactivates the user with the specified ID.
     *
     * @param id The ID of the user to deactivate
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    @Override
    @Transactional
    public void deactivate(Long id) {
        checkIfUserExists(id, false);
        userRepository.updateUserStatus(id, Status.INACTIVE);
    }

    /**
     * Activates the user with the specified ID.
     *
     * @param id The ID of the user to activate
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    @Override
    @Transactional
    public void activate(Long id) {
        checkIfUserExists(id, false);
        userRepository.updateUserStatus(id, Status.ACTIVE);
    }

    /**
     * Retrieves the user with the specified ID.
     *
     * @param id The ID of the user to retrieve
     * @return The User object corresponding to the specified ID
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    @Override
    @Transactional(readOnly = true)
    public User get(Long id) {
        return getById(id);
    }

    /**
     * Deletes the user with the specified ID.
     *
     * @param id The ID of the user to delete
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    @Override
    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    /**
     * Retrieves the user entity with the specified ID.
     *
     * @param userId The ID of the user to retrieve
     * @return The User object corresponding to the specified ID
     * @throws EntityNotFoundException if no user with the given ID is found
     */
    private User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER_MESSAGE + userId));
    }

    /**
     * Checks if a user with the specified ID exists in the database.
     *
     * @param id             The ID of the user to check
     * @param shouldNotExist Whether the user should not exist (true) or should exist (false)
     * @throws EntityAlreadyExistsException if a user with the given ID already exists and shouldNotExist is true
     * @throws EntityNotFoundException      if no user with the given ID exists and shouldNotExist is false
     */
    private void checkIfUserExists(Long id, boolean shouldNotExist) {
        boolean exists = userRepository.existsById(id);
        if (shouldNotExist && exists) {
            throw new EntityAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE + id);
        } else if (!shouldNotExist && !exists) {
            throw new EntityNotFoundException(NOT_FOUND_USER_MESSAGE + id);
        }
    }
}