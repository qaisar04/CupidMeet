package kz.baltabayev.telegrambotservice.keyboard.factory;

import kz.baltabayev.telegrambotservice.keyboard.Keyboard;
import kz.baltabayev.telegrambotservice.keyboard.impl.GenderKeyboard;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KeyboardFactory {

    private final Map<BotState, Keyboard> keyboards;

    @Autowired
    public KeyboardFactory(List<Keyboard> keyboardList) {
        keyboards = new HashMap<>();
        for (Keyboard keyboard : keyboardList) {
            if (keyboard instanceof GenderKeyboard) {
                keyboards.put(BotState.SELECT_GENDER, keyboard);
            }
        }
    }

    public ReplyKeyboardMarkup getKeyboard(BotState state) {
        return keyboards.get(state).getKeyboard(state);
    }
}