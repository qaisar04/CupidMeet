package kz.baltabayev.telegrambotservice.service;

import kz.baltabayev.telegrambotservice.model.types.BotState;

public interface BotStateService {

    void save(Long userID, BotState state);

    BotState get(Long userID);
}