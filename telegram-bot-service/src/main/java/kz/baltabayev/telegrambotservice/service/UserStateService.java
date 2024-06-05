package kz.baltabayev.telegrambotservice.service;

import kz.baltabayev.telegrambotservice.model.entity.UserState;

public interface UserStateService {

    UserState init(Long userId);

    void save(UserState state);

    UserState get(Long userId);

    void updateName(Long userId, String name);

    void updateAge(Long userId, Integer age);

    void updateGender(Long userId, String gender);

    void updateCity(Long userId, String city);

    void updateMbti(Long userId, String mbti);

    void updateBio(Long userId, String about);
}