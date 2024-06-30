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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public static final String NOT_FOUND_USER_MESSAGE = "Not found user with id: ";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists with id: ";

    @Override
    @Transactional
    public void create(Long id, String username) {
        if (userRepository.existsById(id)) {
            throw new EntityAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE + id);
        }
        userRepository.insertUser(id, username, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        User user = getById(id);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user); //todo: improve it by querying the repository :)
    }

    @Override
    public User get(Long id) {
        return getById(id);
    }

    private User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER_MESSAGE + userId));
    }
}