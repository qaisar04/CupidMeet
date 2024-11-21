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
 * Обощенное исключение с сообщениями.
 */
@Getter
public class CustomException extends Exception {

    /**
     * Список сообщений.
     */
    private final List<Message> messages = new ArrayList<>();

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public CustomException(final Message message) {
        super(message.getText());
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param messagesIn сообщения
     */
    public CustomException(final List<Message> messagesIn) {
        super(messagesIn.stream().map(Message::getText).collect(Collectors.joining("; ")));
        this.messages.addAll(messagesIn);
    }

    /**
     * Конструктор.
     *
     * @param value  константа сообщения
     * @param params параметры
     */
    public CustomException(final MessageEnum value, final Object... params) {
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
    public CustomException(final Throwable exception, final Message message) {
        super(exception);
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param exception  исключение
     * @param messagesIn сообщения
     */
    public CustomException(final Throwable exception, final List<Message> messagesIn) {
        super(exception);
        this.messages.addAll(messagesIn);
    }

    /**
     * Конструктор.
     *
     * @param exception исключение
     * @param value     константа сообщения
     * @param params    параметры
     */
    public CustomException(final Throwable exception, final MessageEnum value, final Object... params) {
        super(exception);
        messages.add(new Message(value, MessageLevel.ERROR, params));
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param message сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CustomException> supplier(final Message message) {
        return () -> new CustomException(message);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param messages сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CustomException> supplier(final List<Message> messages) {
        return () -> new CustomException(messages);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param value  константа сообщения
     * @param params параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CustomException> supplier(final MessageEnum value, final Object... params) {
        return () -> new CustomException(value, params);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param exception эксепшн
     * @param message   сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CustomException> supplier(final Throwable exception, final Message message) {
        return () -> new CustomException(exception, message);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param exception исключение
     * @param messages  сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CustomException> supplier(final Throwable exception, final List<Message> messages) {
        return () -> new CustomException(exception, messages);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param exception исключение
     * @param value     константа сообщения
     * @param params    параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CustomException> supplier(final Throwable exception,
                                                     final MessageEnum value,
                                                     final Object... params) {
        return () -> new CustomException(exception, value, params);
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