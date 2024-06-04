package kz.baltabayev.telegrambotservice.handler.factory;

import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class BotStateHandlerFactory {

    private final Map<BotState, BotStateHandler> stateHandlers = new EnumMap<>(BotState.class);

    @Autowired
    public BotStateHandlerFactory(
            @Qualifier("awaitingNameHandler") BotStateHandler awaitingNameHandler,
            @Qualifier("awaitingAgeHandler") BotStateHandler awaitingAgeHandler,
            @Qualifier("awaitingGenderHandler") BotStateHandler awaitingGenderHandler,
            @Qualifier("awaitingImageHandler") BotStateHandler awaitingImageHandler,
            @Qualifier("awaitingBioHandler") BotStateHandler awaitingBioHandler,
            @Qualifier("awaitingPersonalityTypeHandler") BotStateHandler awaitingPersonalityTypeHandler,
            @Qualifier("awaitingCityHandler") BotStateHandler awaitingCityHandler
    ) {
        stateHandlers.put(BotState.AWAITING_NAME, awaitingNameHandler);
        stateHandlers.put(BotState.AWAITING_AGE, awaitingAgeHandler);
        stateHandlers.put(BotState.AWAITING_GENDER, awaitingGenderHandler);
        stateHandlers.put(BotState.AWAITING_PHOTO, awaitingImageHandler);
        stateHandlers.put(BotState.AWAITING_BIO, awaitingBioHandler);
        stateHandlers.put(BotState.AWAITING_PERSONALITY_TYPE, awaitingPersonalityTypeHandler);
        stateHandlers.put(BotState.AWAITING_CITY, awaitingCityHandler);
    }

    public BotStateHandler getHandler(BotState state) {
        return stateHandlers.get(state);
    }
}