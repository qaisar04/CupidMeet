package com.cupidmeet.runtimecore.response;

import com.cupidmeet.runtimecore.exception.CustomException;
import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.runtimecore.message.Message;
import com.cupidmeet.runtimecore.message.MessageEnum;
import com.cupidmeet.runtimecore.message.MessageLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Общий класс ответа на запрос.
 */
@Setter
@Getter
@NoArgsConstructor
public class CommonResponse {

    /**
     * Timestamp.
     */
    @Schema(example = "2021-10-04T00:00:01.1111112")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Список детализированных сообщений.
     */
    private List<Message> messages = new ArrayList<>();

    public CommonResponse(final Message message) {
        messages.add(message);
    }

    public CommonResponse(final List<Message> messagesIn) {
        this.messages = messagesIn;
    }

    public CommonResponse(final CustomException customException) {
        messages.addAll(customException.getMessages());
    }

    public CommonResponse(final CustomRuntimeException customRuntimeException) {
        messages.addAll(customRuntimeException.getMessages());
    }

    public CommonResponse(final MessageEnum messageEnum, final Object... params) {
        Message message = new Message(messageEnum, MessageLevel.INFO, params);
        messages.add(message);
    }

    public CommonResponse(final MessageLevel level, final MessageEnum messageEnum, final Object... params) {
        Message message = new Message(messageEnum, level, params);
        messages.add(message);
    }

    public void addMessage(final Message message) {
        messages.add(message);
    }
}