package com.cupidmeet.commonmessage.commonresponse;

import com.cupidmeet.commonmessage.exception.CommonException;
import com.cupidmeet.commonmessage.exception.CommonRuntimeException;
import com.cupidmeet.commonmessage.message.Message;
import com.cupidmeet.commonmessage.message.MessageEnum;
import com.cupidmeet.commonmessage.message.MessageLevel;
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

    public CommonResponse(final CommonException commonException) {
        messages.addAll(commonException.getMessages());
    }

    public CommonResponse(final CommonRuntimeException commonRuntimeException) {
        messages.addAll(commonRuntimeException.getMessages());
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