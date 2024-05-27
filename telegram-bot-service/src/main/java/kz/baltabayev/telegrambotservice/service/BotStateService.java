package kz.baltabayev.telegrambotservice.service;

import kz.baltabayev.telegrambotservice.model.types.BotState;

import java.util.UUID;

public interface BotStateService {

    void save(UUID userID, BotState state);

    BotState get(UUID userID);
}
