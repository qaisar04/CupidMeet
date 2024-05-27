package kz.baltabayev.telegrambotservice.command.factory;

import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.command.impl.StartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandFactory {

    private final Map<String, Command> commands = new HashMap<>();

    @Autowired
    public CommandFactory(StartCommand startCommand) {
        commands.put("/start", startCommand);
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }
}