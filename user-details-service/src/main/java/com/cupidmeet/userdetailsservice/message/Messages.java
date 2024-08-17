package com.cupidmeet.userdetailsservice.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

    NOT_FOUND(1, "{0} с {1} {2} отсутствует"),

    ALREADY_EXISTS_TELEGRAM(2, "{1} с идентификатором {2} (Telegram ID) уже существует"),

    ALREADY_EXISTS(3, "{1} с идентификатором {2} уже существует");

    /**
     * Код ошибки.
     */
    private final int code;

    /**
     * Формат сообщения.
     */
    private final String textPattern;
}
