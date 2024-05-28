package kz.baltabayev.telegrambotservice.selector;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

public class LanguageSelector {

    public static SendMessage createLanguageSelectionMessage(Long chatId) {

        InlineKeyboardMarkup replyMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(
                        (
                                new InlineKeyboardRow(
                                        InlineKeyboardButton.builder()
                                                .text("Қазақ")
                                                .callbackData("lang_kz")
                                                .build())
                        )
                ).keyboardRow(
                        (
                                new InlineKeyboardRow(
                                        InlineKeyboardButton.builder()
                                                .text("Русский")
                                                .callbackData("lang_ru")
                                                .build())
                        )
                ).keyboardRow(
                        (
                                new InlineKeyboardRow(
                                        InlineKeyboardButton.builder()
                                                .text("English")
                                                .callbackData("lang_en")
                                                .build())
                        )
                ).build();

        return SendMessage.builder()
                .chatId(chatId)
                .text("Выберите свой язык:")
                .replyMarkup(replyMarkup)
                .build();
    }
}