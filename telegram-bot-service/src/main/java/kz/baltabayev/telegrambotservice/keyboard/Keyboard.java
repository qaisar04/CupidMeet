package kz.baltabayev.telegrambotservice.keyboard;

import kz.baltabayev.telegrambotservice.model.types.BotState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface Keyboard {
    ReplyKeyboardMarkup getKeyboard(BotState state);
}