package kz.baltabayev.telegrambotservice.service.impl;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.repository.UserStateRepository;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStateServiceImpl implements UserStateService {

    private final UserStateRepository userStateRepository;
    private final BotStateService botStateService;

    @Override
    public UserState init(Long userId) {
        UserState state = new UserState(userId);
        botStateService.save(userId, BotState.NONE);
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
}