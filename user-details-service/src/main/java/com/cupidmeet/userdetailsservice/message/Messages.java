package com.cupidmeet.userdetailsservice.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

    NOT_FOUND(1, "%s с %s %s отсутствует"),

    ALREADY_EXISTS_TELEGRAM(2, "%s с идентификатором %s (Telegram ID) уже существует"),

    ALREADY_EXISTS(3, "%s с идентификатором %s уже существует");

    /**
     * Код ошибки.
     */
    private final int code;

    /**
     * Формат сообщения.
     */
    private final String textPattern;
}
