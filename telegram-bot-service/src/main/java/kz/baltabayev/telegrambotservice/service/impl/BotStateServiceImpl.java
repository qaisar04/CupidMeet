package kz.baltabayev.telegrambotservice.service.impl;

import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BotStateServiceImpl implements BotStateService {

    private final RedisTemplate<String, BotState> redisTemplate;
    private static final int ACTIVATION_TIME_IN_MINUTES = 20;

    @Override
    public void save(Long userID, BotState state) {
        String key = userID.toString();
        redisTemplate.opsForValue().set(key, state);
        redisTemplate.expire(key, ACTIVATION_TIME_IN_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public BotState get(Long userID) {
        String key = userID.toString();
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .orElse(BotState.NONE);
    }
}