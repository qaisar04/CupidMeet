package kz.baltabayev.telegrambotservice.keyboard.impl;

import kz.baltabayev.telegrambotservice.keyboard.Keyboard;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.util.KeyboardBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenderKeyboard implements Keyboard {

    @Override
    public ReplyKeyboardMarkup getKeyboard(BotState state) {
        List<List<String>> genderButtons = new ArrayList<>();
        genderButtons.add(List.of("MALE", "FEMALE"));
        return KeyboardBuilder.buildKeyboard(genderButtons, true, true, true);
    }
}