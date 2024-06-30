package kz.baltabayev.userdetailsservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.userdetailsservice.exception.EntityAlreadyExistsException;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import kz.baltabayev.userdetailsservice.repository.UserPreferenceRepository;
import kz.baltabayev.userdetailsservice.service.UserPreferenceService;
import kz.baltabayev.userdetailsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserService userService;
    private final UserPreferenceRepository userPreferenceRepository;

    public static final String NOT_FOUND_MESSAGE = "Not found userPreference for the user with id: ";
    public static final String ALREADY_EXISTS_MESSAGE = "UserPreference already exists for user with id: ";

    @Override
    public void create(Long userId, String gender) {
        if (userPreferenceRepository.existsByUserId(userId)) {
            throw new EntityAlreadyExistsException(ALREADY_EXISTS_MESSAGE + userId);
        }

        PreferredGender preferredGender = PreferredGender.fromString(gender);

        User user = userService.get(userId);
        Integer age = user.getUserInfo().getAge();
        UserPreference userPreference = new UserPreference(preferredGender, age + 5, age - 5, user);
        userPreferenceRepository.save(userPreference);
    }

    @Override
    public void update(Long userId, String gender, Integer maxAge, Integer minAge) {
        UserPreference preference = getByIdUserId(userId);
        PreferredGender preferredGender = PreferredGender.fromString(gender);

        preference.setPreferredGender(preferredGender);
        preference.setMaxAge(maxAge);
        preference.setMinAge(minAge);

        userPreferenceRepository.save(preference);
    }

    private UserPreference getByIdUserId(Long userId) {
        return userPreferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + userId));
    }
}