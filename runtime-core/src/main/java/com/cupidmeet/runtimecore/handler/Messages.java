package com.cupidmeet.runtimecore.handler;

import com.cupidmeet.runtimecore.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages implements MessageEnum {

    /**
     * Ошибка валидации.
     */
    VALIDATION_ERROR(4, "Ошибка валидации: поле {0} - {1}"),

    /**
     * Чтобы не править последний код.
     */
    DUMMY_MESSAGE(999, "DUMMY");

    /**
     * Имя множества сообщений.
     */
    public static final String SOURCE = "cupid-meet";

    /**
     * Код ошибки.
     */
    private final int code;

    /**
     * Формат сообщения.
     */
    private final String textPattern;

    @Override
    public String getSource() {
        return SOURCE;
    }
}