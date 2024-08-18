package com.cupidmeet.commonmessage.message;

/**
 * Сообщение.
 */
public interface MessageEnum {

    /**
     * Получить код.
     *
     * @return код
     */
    int getCode();

    /**
     * Получить шаблон сообщения.
     *
     * @return шаблон
     */
    String getTextPattern();

    /**
     * Получить источник.
     *
     * @return источник
     */
    String getSource();
}
