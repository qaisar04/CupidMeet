package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.types.Status;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public static final String NOT_FOUND_USER_MESSAGE = "Not found user with id: ";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists with id: ";

    @Override
    @Transactional
    public void create(Long id, String username) {
        checkIfUserExists(id, true);
        userRepository.insertUser(id, username);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        checkIfUserExists(id, false);
        userRepository.updateUserStatus(id, Status.INACTIVE);
    }

    @Override
    @Transactional
    public void activate(Long id) {
        checkIfUserExists(id, false);
        userRepository.updateUserStatus(id, Status.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public User get(Long id) {
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    private User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER_MESSAGE + userId));
    }

    private void checkIfUserExists(Long id, boolean shouldNotExist) {
        boolean exists = userRepository.existsById(id);
        if (shouldNotExist && exists) {
            throw new EntityAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE + id);
        } else if (!shouldNotExist && !exists) {
            throw new EntityNotFoundException(NOT_FOUND_USER_MESSAGE + id);
        }
    }
}