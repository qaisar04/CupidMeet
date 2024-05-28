package kz.baltabayev.telegrambotservice.handler.factory;

import kz.baltabayev.telegrambotservice.handler.DialogHandler;
import kz.baltabayev.telegrambotservice.handler.ExtendedDialogHandler;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DialogHandlerFactory {

    private final Map<BotState, DialogHandler> handlers = new HashMap<>();

    @Autowired
    public DialogHandlerFactory(ExtendedDialogHandler extendedDialogHandler) {
        handlers.put(BotState.NONE, extendedDialogHandler);
        handlers.put(BotState.AWAITING_FIRST, extendedDialogHandler);
        handlers.put(BotState.AWAITING_SECOND, extendedDialogHandler);
    }

    public DialogHandler getHandler(BotState state) {
        return handlers.get(state);
    }
}