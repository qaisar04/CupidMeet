package com.cupidmeet.qrservice.message;

import com.cupidmeet.runtimecore.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages implements MessageEnum {

    OK(0, "OK"),

    NOT_FOUND(1, "{0} с {1} {2} отсутствует"),

    QR_GENERATION_ERROR(2, "Генерация QR для {0} завершилась ошибкой, причина: {1}");

    public static final String SOURCE = "qr-service";

    private final int code;

    private final String textPattern;

    @Override
    public String getSource() {
        return SOURCE;
    }
}
