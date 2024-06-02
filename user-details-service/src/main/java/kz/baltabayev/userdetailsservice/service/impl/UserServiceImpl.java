package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.repository.UserRepository;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public static final String NOT_FOUND_IDEA_MESSAGE = "Not found idea with id: ";

    @Override
    public void create(Long id, String username) {
        User user = new User(id, username);
        userRepository.save(user); //todo custom query
    }

    @Override
    public User get(Long id) {
        return getById(id);
    }

    private User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_IDEA_MESSAGE + userId));
    }
}