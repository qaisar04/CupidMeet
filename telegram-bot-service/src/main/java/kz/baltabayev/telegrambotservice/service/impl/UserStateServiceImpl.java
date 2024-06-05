package kz.baltabayev.telegrambotservice.service.impl;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.repository.UserStateRepository;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStateServiceImpl implements UserStateService {

    private final UserStateRepository userStateRepository;

    @Override
    public UserState init(Long userId) {
        UserState state = new UserState(userId);
        state.setLanguage("lang_ru");
        userStateRepository.save(state);
        return state;
    }

    @Override
    public void save(UserState state) {
        userStateRepository.save(state);
    }

    @Override
    public UserState get(Long userId) {
        return userStateRepository.findById(userId)
                .orElseGet(() -> init(userId));
    }

    @Transactional
    public void updateName(Long userId, String name) {
        userStateRepository.updateName(userId, name);
    }

    @Transactional
    public void updateAge(Long userId, Integer age) {
        userStateRepository.updateAge(userId, age);
    }

    @Transactional
    public void updateGender(Long userId, String gender) {
        userStateRepository.updateGender(userId, gender);
    }

    @Transactional
    public void updateMbti(Long userId, String mbti) {
        userStateRepository.updateMbti(userId, mbti);
    }

    @Transactional
    public void updateCity(Long userId, String city) {
        userStateRepository.updateCity(userId, city);
    }

    @Override
    public void updateBio(Long userId, String about) {
        userStateRepository.updateBio(userId, about);
    }
}