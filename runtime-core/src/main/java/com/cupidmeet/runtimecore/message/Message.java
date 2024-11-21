package com.cupidmeet.runtimecore.message;

import com.cupidmeet.runtimecore.exception.MessageDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Класс сообщений.
 */
@Getter
public class Message implements Serializable {

    /**
     * Константа сообщения.
     */
    @JsonIgnore
    private MessageDto value;

    /**
     * Источник сообщения (берется из множества кодов сообщений).
     */
    @JsonProperty("source")
    @Schema(example = "Backend")
    private String source;

    /**
     * Код сообщения.
     */
    @JsonProperty("code")
    @Schema(example = "1")
    private int code;

    /**
     * Уровень сообщения INFO, WARNING, ERROR.
     */
    @JsonProperty("level")
    @Schema(example = "ERROR")
    private MessageLevel level;

    /**
     * Параметры.
     */
    @JsonProperty("parameters")
    @Schema(example = "[user, id, 7]")
    private Object[] parameters;

    /**
     * Сообщение текстом.
     */
    @JsonProperty("text")
    @Schema(example = "Entity user with id = 7 not found")
    private String text;

    /**
     * Конструктор.
     *
     * @param valueIn      константа сообщения
     * @param levelIn      уровень сообщения INFO, WARNING, ERROR
     * @param parametersIn параметры
     */
    public Message(final MessageEnum valueIn,
                   final MessageLevel levelIn,
                   final Object... parametersIn) {
        this(valueIn, parametersIn);
        this.level = levelIn;
    }

    /**
     * Конструктор уровень сообщения по умолчанию - ошибка.
     *
     * @param valueIn      константа сообщения
     * @param parametersIn параметры
     */
    public Message(final MessageEnum valueIn,
                   final Object... parametersIn) {
        this.value = new MessageDto(valueIn);
        this.parameters = parametersIn;
        this.code = value.getCode();
        this.text = MessageFormat.format(value.getTextPattern(), parameters);
        this.source = value.getSource();
        this.level = MessageLevel.ERROR;
    }
}
