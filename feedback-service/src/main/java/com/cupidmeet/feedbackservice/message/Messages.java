package com.cupidmeet.feedbackservice.message;

import com.cupidmeet.runtimecore.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages implements MessageEnum {

    OK(0, "OK"),

    NOT_FOUND(1, "{0} с {1} {2} отсутствует"),

    ALREADY_EXISTS(2, "{0} с идентификатором {1} уже существует"),

    VALIDATION_ERROR(3, "Ошибка валидации: поле {0} - {1}"),

    COMPLAINT_ALREADY_EXIST(4, "Пользователь с идентификатором {1} уже подавал ранее жалобу на пользователя с идентификатором {2}"),

    COMPLAINT_SELF_NOT_ALLOWED(5, "Жалоба не должна быть отправлена на самого себя"),

    DUPLICATE_FEEDBACK_SUBMISSION(6, "Попытка повторной отправки фидбека недопустима");

    public static final String SOURCE = "feedback-service";

    private final int code;

    private final String textPattern;

    @Override
    public String getSource() {
        return SOURCE;
    }
}