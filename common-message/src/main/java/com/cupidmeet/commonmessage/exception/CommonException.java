package com.cupidmeet.commonmessage.exception;

import com.cupidmeet.commonmessage.message.Message;
import com.cupidmeet.commonmessage.message.MessageEnum;
import com.cupidmeet.commonmessage.message.MessageLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Обощенное исключение с сообщениями.
 */
@Getter
public class CommonException extends Exception {

    /**
     * Список сообщений.
     */
    private final List<Message> messages = new ArrayList<>();

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public CommonException(final Message message) {
        super(message.getText());
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param messagesIn сообщения
     */
    public CommonException(final List<Message> messagesIn) {
        super(messagesIn.stream().map(Message::getText).collect(Collectors.joining("; ")));
        this.messages.addAll(messagesIn);
    }

    /**
     * Конструктор.
     *
     * @param value  константа сообщения
     * @param params параметры
     */
    public CommonException(final MessageEnum value, final Object... params) {
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
    public CommonException(final Throwable exception, final Message message) {
        super(exception);
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param exception  исключение
     * @param messagesIn сообщения
     */
    public CommonException(final Throwable exception, final List<Message> messagesIn) {
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
    public CommonException(final Throwable exception, final MessageEnum value, final Object... params) {
        super(exception);
        messages.add(new Message(value, MessageLevel.ERROR, params));
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param message сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CommonException> supplier(final Message message) {
        return () -> new CommonException(message);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param messages сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CommonException> supplier(final List<Message> messages) {
        return () -> new CommonException(messages);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param value  константа сообщения
     * @param params параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CommonException> supplier(final MessageEnum value, final Object... params) {
        return () -> new CommonException(value, params);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param exception эксепшн
     * @param message   сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CommonException> supplier(final Throwable exception, final Message message) {
        return () -> new CommonException(exception, message);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param exception исключение
     * @param messages  сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CommonException> supplier(final Throwable exception, final List<Message> messages) {
        return () -> new CommonException(exception, messages);
    }

    /**
     * Получить порождающий интерфейс.
     *
     * @param exception исключение
     * @param value     константа сообщения
     * @param params    параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CommonException> supplier(final Throwable exception,
                                                     final MessageEnum value,
                                                     final Object... params) {
        return () -> new CommonException(exception, value, params);
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