package com.cupidmeet.userdetailsservice.message;

import com.cupidmeet.runtimecore.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages implements MessageEnum {

    OK(0, "OK"),

    NOT_FOUND(1, "{0} с {1} {2} отсутствует"),

    ALREADY_EXISTS(2, "{0} с идентификатором {1} уже существует"),

    SELF_REACTION_NOT_ALLOWED(3, "В системе запрещено оценивать самого себя"),

    VALIDATION_ERROR(4, "Ошибка валидации: поле {0} - {1}"),

    UNAUTHORIZED_ERROR(5, "Для назначения ролей требуются права администратора."),

    ROLE_ALREADY_ASSIGNED(6, "У пользователя уже есть указанная роль."),

    ATTACHMENT_ALREADY_DELETED(7, "Файл вложения с идентификатором {0} уже помечен как удалён."),

    DOMAIN_USERNAME_NOT_FOUND(8, "Не найдено доменное имя пользователя");

    public static final String SOURCE = "user-details-service";

    private final int code;

    private final String textPattern;

    @Override
    public String getSource() {
        return SOURCE;
    }
}