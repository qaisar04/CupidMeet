package com.cupidmeet.runtimecore.message;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Уровень сообщения: инфо, предупреждение, ошибка.
 */
public enum MessageLevel {

    /**
     * Информационное.
     */
    INFO("INFO"),

    /**
     * Предупреждение.
     */
    WARNING("WARNING"),

    /**
     * Ошибка.
     */
    ERROR("ERROR");

    /**
     * Значение.
     */
    private String value;

    /**
     * Конструктор.
     *
     * @param val значение
     */
    MessageLevel(final String val) {
        this.value = val;
    }

    /**
     * toString.
     *
     * @return значение
     */
    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}