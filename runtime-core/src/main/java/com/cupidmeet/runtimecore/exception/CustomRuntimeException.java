package com.cupidmeet.runtimecore.exception;

import com.cupidmeet.runtimecore.message.Message;
import com.cupidmeet.runtimecore.message.MessageEnum;
import com.cupidmeet.runtimecore.message.MessageLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * RuntimeException, содержащий список сообщений от бэка.
 */
@Getter
public class CustomRuntimeException extends RuntimeException {

    /**
     * Список детализированных сообщений.
     */
    private final List<Message> messages = new ArrayList<>();

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public CustomRuntimeException(final Message message) {
        super(message.getText());
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param messagesIn сообщения
     */
    public CustomRuntimeException(final List<Message> messagesIn) {
        super(messagesIn.stream().map(Message::getText).collect(Collectors.joining("; ")));
        this.messages.addAll(messagesIn);
    }

    /**
     * Конструктор.
     *
     * @param value  константа сообщения
     * @param params параметры
     */
    public CustomRuntimeException(final MessageEnum value, final Object... params) {
        super(new Message(value, MessageLevel.ERROR, params).getText());
        Message message = new Message(value, MessageLevel.ERROR, params);
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param exception исключение
     * @param message   сообщение
     */
    public CustomRuntimeException(final Throwable exception, final Message message) {
        super(exception);
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param exception  исключение
     * @param messagesIn сообщения
     */
    public CustomRuntimeException(final Throwable exception, final List<Message> messagesIn) {
        super(exception);
        this.messages.addAll(messagesIn);
    }

    /**
     * Конструктор.
     *
     * @param exception исключение
     * @param value     константа сообщения
     * @param params    праметры
     */
    public CustomRuntimeException(final Throwable exception, final MessageEnum value, final Object... params) {
        super(exception);
        messages.add(new Message(value, MessageLevel.ERROR, params));
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param message сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CustomRuntimeException> supplier(final Message message) {
        return () -> new CustomRuntimeException(message);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param messages сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CustomRuntimeException> supplier(final List<Message> messages) {
        return () -> new CustomRuntimeException(messages);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param value  константа сообщения
     * @param params параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CustomRuntimeException> supplier(final MessageEnum value, final Object... params) {
        return () -> new CustomRuntimeException(value, params);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param exception эксепшн
     * @param message   сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CustomRuntimeException> supplier(final Throwable exception, final Message message) {
        return () -> new CustomRuntimeException(exception, message);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param exception эксепшн
     * @param messages  сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CustomRuntimeException> supplier(final Throwable exception, final List<Message> messages) {
        return () -> new CustomRuntimeException(exception, messages);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param exception эксепшн
     * @param value     константа сообщения
     * @param params    параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CustomRuntimeException> supplier(final Throwable exception,
                                                            final MessageEnum value,
                                                            final Object... params) {
        return () -> new CustomRuntimeException(exception, value, params);
    }

    /**
     * Добавить сообщение.
     *
     * @param message сообщение
     */
    public void addMessage(final Message message) {
        messages.add(message);
    }
}