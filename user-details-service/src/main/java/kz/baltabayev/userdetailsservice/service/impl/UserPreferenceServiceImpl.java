package kz.baltabayev.userdetailsservice.service.impl;

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

    @Override
    public void create(Long userId, String gender) {
        User user = userService.get(userId);
        Integer age = user.getUserInfo().getAge();
        UserPreference userPreference = new UserPreference(PreferredGender.valueOf(gender.toUpperCase()), age + 5, age - 5, user);
        userPreferenceRepository.save(userPreference);
    }

    @Override
    public void update(Long userId, String gender, Integer maxAge, Integer minAge) {
        User user = userService.get(userId);
        UserPreference preference = user.getUserPreference();
        preference.setPreferredGender(PreferredGender.valueOf(gender.toUpperCase()));
        preference.setMaxAge(maxAge);
        preference.setMinAge(minAge);
        userPreferenceRepository.save(preference);
    }
}