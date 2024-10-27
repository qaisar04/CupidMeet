package com.cupidmeet.notificationservice.producer;

import com.cupidmeet.notificationservice.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, Message> template;

    @Scheduled(fixedRate = 60000)
    public void send(){
        Message message = new Message();
        message.setSenderName("hello wrold");
        message.setMessage("Hello World");
        message.setReceiverEmail("email");
        message.setReceiverName("name");
        message.setSenderProfileUrl("some url");

        CompletableFuture<SendResult<String, Message>> future = template.send("notification-topic", UUID.randomUUID().toString(),message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}
