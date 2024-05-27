package kz.baltabayev.telegrambotservice.command;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface Command {

    void execute(Message message);
}