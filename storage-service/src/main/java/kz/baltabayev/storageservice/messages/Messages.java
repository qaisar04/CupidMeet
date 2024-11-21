package kz.baltabayev.storageservice.messages;

import com.cupidmeet.runtimecore.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages implements MessageEnum {

    OK(0, "OK"),

    NOT_FOUND(1, "{0} с {1} {2} отсутствует"),

    VALIDATION_ERROR(4, "Ошибка валидации: поле {0} - {1}"),

    FILE_NOT_FOUND(5, "Файл с местоположением: {0} отсутствует или не может быть прочитан"),

    WRITING_TO_STORAGE_FAILS(6, "Ошибка записи файла в хранилище"),

    FILE_DELETE_FAILS(7, "Ошибка удаления файла с местоположением: {0}"),

    DUMMY_MESSAGE(999, "DUMMY");

    public static final String SOURCE = "storage";

    private final int code;

    private final String textPattern;

    @Override
    public String getSource() {
        return SOURCE;
    }
}