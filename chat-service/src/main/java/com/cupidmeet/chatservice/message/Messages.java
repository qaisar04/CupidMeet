package com.cupidmeet.chatservice.message;

import com.cupidmeet.runtimecore.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages implements MessageEnum {

    OK(0, "OK"),

    NOT_FOUND(1, "{0} с {1} {2} отсутствует"),

    VALIDATION_ERROR(4, "Ошибка валидации: поле {0} - {1}"),

    CHAT_TYPE_IS_NULL(5, "Тип чата не может быть пустым"),

    CHAT_NOT_ALLOWED(6, "Создания чата невозможна. Причина: {0}"),

    DUMMY_MESSAGE(999, "DUMMY");

    public static final String SOURCE = "storage";

    private final int code;

    private final String textPattern;

    @Override
    public String getSource() {
        return SOURCE;
    }
}