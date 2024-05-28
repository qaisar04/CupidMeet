package kz.baltabayev.telegrambotservice.service;

import kz.baltabayev.telegrambotservice.model.entity.UserState;

public interface UserStateService {

    UserState init(Long userId);

    void save(UserState state);

    UserState get(Long userId);
}