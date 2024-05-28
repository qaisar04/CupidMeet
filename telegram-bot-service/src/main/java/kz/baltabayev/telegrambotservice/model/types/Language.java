package kz.baltabayev.telegrambotservice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    EN("lang_en", "en", "You have selected English."),
    RU("lang_ru", "ru", "Вы выбрали русский язык."),
    KZ("lang_kz", "kz", "Сіз қазақ тілін таңдадыңыз.");

    private final String code;
    private final String language;
    private final String selectionMessage;

    public static Language fromCode(String code) {
        for (Language lang : values()) {
            if (lang.code.equals(code)) {
                return lang;
            }
        }

        return null;
    }
}