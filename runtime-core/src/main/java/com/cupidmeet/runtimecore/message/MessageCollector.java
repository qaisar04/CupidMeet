package com.cupidmeet.runtimecore.message;

import com.cupidmeet.runtimecore.exception.CustomException;
import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Сборщик сообщений.
 */
@Getter
@NoArgsConstructor
public class MessageCollector {

    /**
     * Список детализированных сообщений.
     */
    private List<Message> messages = new ArrayList<>();

    /**
     * Добавить сообщение.
     *
     * @param value      константа сообщения
     * @param level      Уровень сообщения
     * @param parameters параметры
     */
    public void add(final MessageEnum value, final MessageLevel level, final Object... parameters) {
        messages.add(new Message(value, level, parameters));
    }

    /**
     * Добавить сообщение об ошибке.
     *
     * @param value      константа сообщения
     * @param parameters параметры
     */
    public void addError(final MessageEnum value, final Object... parameters) {
        messages.add(new Message(value, MessageLevel.ERROR, parameters));
    }

    /**
     * Каррирование.
     *
     * @param value константа сообщения
     * @param level Уровень сообщения
     * @return каррирование
     */
    public Currying currying(final MessageEnum value, final MessageLevel level) {
        return (final Object... parameters) -> add(value, level, parameters);
    }

    /**
     * Добавить список сообщений.
     *
     * @param messagesIn список сообщений
     */
    public void addAll(final List<Message> messagesIn) {
        messages.addAll(messagesIn);
    }

    /**
     * Проверить наличие сообщений об ошибке.
     *
     * @return флаг
     */
    public boolean hasError() {
        return messages.stream().anyMatch(message -> message.getLevel().equals(MessageLevel.ERROR));
    }

    /**
     * Бросить CommonException при наличии ошибки.
     */
    public void throwCommonExceptionIfHasError() throws CustomException {
        if (hasError()) {
            throw new CustomException(messages);
        }
    }

    /**
     * Бросить CommonException при наличии ошибки.
     */
    public void throwCommonRuntimeExceptionIfHasError() {
        if (hasError()) {
            throw new CustomRuntimeException(messages);
        }
    }

    /**
     * Интерфейс каррирования.
     */
    public interface Currying {
        /**
         * Добавить сообщение.
         *
         * @param parameters параметры
         */
        void add(Object... parameters);
    }
}