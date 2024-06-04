package kz.baltabayev.telegrambotservice.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardBuilder {

    /**
     * Создает клавиатуру с заданными кнопками.
     *
     * @param buttonRows Список строк с кнопками. Каждая строка представлена списком кнопок.
     * @param resizeKeyboard Опционально. Запросить изменение размера клавиатуры. По умолчанию false.
     * @param oneTimeKeyboard Опционально. Запросить скрытие клавиатуры после использования. По умолчанию false.
     * @param selective Опционально. Показать клавиатуру только для определенных пользователей. По умолчанию false.
     * @return Сформированная клавиатура.
     */
    public static ReplyKeyboardMarkup buildKeyboard(List<List<String>> buttonRows,
                                                    Boolean resizeKeyboard,
                                                    Boolean oneTimeKeyboard,
                                                    Boolean selective) {
        List<KeyboardRow> keyboard = new ArrayList<>();

        for (List<String> buttonRow : buttonRows) {
            KeyboardRow row = new KeyboardRow();
            for (String buttonText : buttonRow) {
                row.add(new KeyboardButton(buttonText));
            }
            keyboard.add(row);
        }

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .resizeKeyboard(resizeKeyboard != null ? resizeKeyboard : false)
                .oneTimeKeyboard(oneTimeKeyboard != null ? oneTimeKeyboard : false)
                .selective(selective != null ? selective : false)
                .build();
    }

    // Перегруженный метод для удобства, если дополнительные параметры не нужны
    public static ReplyKeyboardMarkup buildKeyboard(List<List<String>> buttonRows) {
        return buildKeyboard(buttonRows, false, false, false);
    }
}
