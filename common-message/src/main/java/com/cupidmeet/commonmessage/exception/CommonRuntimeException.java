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
 * RuntimeException, содержащий список сообщений от бэка.
 */
@Getter
public class CommonRuntimeException extends RuntimeException {

    /**
     * Список детализированных сообщений.
     */
    private final List<Message> messages = new ArrayList<>();

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public CommonRuntimeException(final Message message) {
        super(message.getText());
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param messagesIn сообщения
     */
    public CommonRuntimeException(final List<Message> messagesIn) {
        super(messagesIn.stream().map(Message::getText).collect(Collectors.joining("; ")));
        this.messages.addAll(messagesIn);
    }

    /**
     * Конструктор.
     *
     * @param value  константа сообщения
     * @param params параметры
     */
    public CommonRuntimeException(final MessageEnum value, final Object... params) {
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
    public CommonRuntimeException(final Throwable exception, final Message message) {
        super(exception);
        messages.add(message);
    }

    /**
     * Конструктор.
     *
     * @param exception  исключение
     * @param messagesIn сообщения
     */
    public CommonRuntimeException(final Throwable exception, final List<Message> messagesIn) {
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
    public CommonRuntimeException(final Throwable exception, final MessageEnum value, final Object... params) {
        super(exception);
        messages.add(new Message(value, MessageLevel.ERROR, params));
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param message сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CommonRuntimeException> supplier(final Message message) {
        return () -> new CommonRuntimeException(message);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param messages сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CommonRuntimeException> supplier(final List<Message> messages) {
        return () -> new CommonRuntimeException(messages);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param value  константа сообщения
     * @param params параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CommonRuntimeException> supplier(final MessageEnum value, final Object... params) {
        return () -> new CommonRuntimeException(value, params);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param exception эксепшн
     * @param message   сообщение
     * @return порождающий интерфейс
     */
    public static Supplier<CommonRuntimeException> supplier(final Throwable exception, final Message message) {
        return () -> new CommonRuntimeException(exception, message);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param exception эксепшн
     * @param messages  сообщения
     * @return порождающий интерфейс
     */
    public static Supplier<CommonRuntimeException> supplier(final Throwable exception, final List<Message> messages) {
        return () -> new CommonRuntimeException(exception, messages);
    }

    /**
     * Генерация порождающего интерфейса.
     *
     * @param exception эксепшн
     * @param value     константа сообщения
     * @param params    параметры
     * @return порождающий интерфейс
     */
    public static Supplier<CommonRuntimeException> supplier(final Throwable exception,
                                                            final MessageEnum value,
                                                            final Object... params) {
        return () -> new CommonRuntimeException(exception, value, params);
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