package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.exception.RoleAlreadyAssignedException;
import kz.baltabayev.userdetailsservice.exception.UnauthorizedException;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import kz.baltabayev.userdetailsservice.model.types.Role;
import kz.baltabayev.userdetailsservice.model.types.Status;
import kz.baltabayev.userdetailsservice.repository.UserInfoRepository;
import kz.baltabayev.userdetailsservice.repository.UserPreferenceRepository;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserInfoService;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserService interface providing operations for managing user entities.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
    private final UserPreferenceService userPreferenceService;
    private final UserInfoRepository userInfoRepository;
    private final UserPreferenceRepository userPreferenceRepository;

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
     * @param user The user object containing all necessary information to create the user, including ID,
     *             username, additional user information, and preferences.
     * @throws EntityAlreadyExistsException if a user with the given ID already exists.
     */
    @Override
    @Transactional
    public void create(User user) {
        checkIfUserExists(user.getId(), true);
        userRepository.save(user);
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
     * Blocks the user with the specified ID.
     *
     * @param id The ID of the user to block.
     */
    @Override
    public void block(Long id) {
        checkIfUserExists(id, false);
        userRepository.updateUserStatus(id, Status.BANNED);
    }

    /**
     * Assigns a role to the user with the specified ID.
     *
     * @param adminId The ID of the admin assigning the role
     * @param userId  The ID of the user to assign the role to
     * @param role    The role to assign
     * @throws UnauthorizedException        if the user performing the operation is not an admin
     * @throws EntityNotFoundException      if no user with the given ID is found
     * @throws RoleAlreadyAssignedException if the user already has the specified role
     */
    @Override
    public void assignRole(Long adminId, Long userId, Role role) {
        if (!isAdmin(adminId)) {
            throw new UnauthorizedException("Admin rights are required to assign roles.");
        }

        User user = getById(userId);
        if (user.getRole().equals(role)) {
            throw new RoleAlreadyAssignedException("The user already has the specified role.");
        }

        user.setRole(role);
        userRepository.saveAndFlush(user);
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

    /**
     * Checks if the user with the specified ID is an admin.
     *
     * @param userId The ID of the user to check
     */
    private boolean isAdmin(Long userId) {
        User byId = getById(userId);
        return byId.getRole().equals(Role.ADMIN);
    }
}