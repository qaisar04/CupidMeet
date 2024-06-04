package kz.baltabayev.telegrambotservice.model.types;

public enum BotState {
    NONE,
    DEFAULT,
    SELECT_LANGUAGE,
    SELECT_GENDER,
    SELECT_PREFERENSE_GENDER,
    AWAITING_NAME,
    AWAITING_AGE,
    AWAITING_GENDER,
    AWAITING_PHOTO,
    AWAITING_BIO,
    AWAITING_PERSONALITY_TYPE,
    AWAITING_CITY,
    COMPLETED
}