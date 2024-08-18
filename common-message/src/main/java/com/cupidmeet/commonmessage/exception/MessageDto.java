package com.cupidmeet.commonmessage.exception;

import com.cupidmeet.commonmessage.message.MessageEnum;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class MessageDto implements MessageEnum, Serializable {

    int code;
    String textPattern;
    String source;

    /**
     * Дто для передачи сообщений по межсервису.
     */
    public MessageDto(final MessageEnum inValue) {
        this.code = inValue.getCode();
        this.textPattern = inValue.getTextPattern();
        this.source = inValue.getSource();
    }
}