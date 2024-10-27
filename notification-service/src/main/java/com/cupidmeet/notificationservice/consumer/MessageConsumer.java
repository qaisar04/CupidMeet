package com.cupidmeet.notificationservice.consumer;

import com.cupidmeet.notificationservice.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    @KafkaListener(topics = "notification-topic")
    public void read(ConsumerRecord<String, Message> consumerRecord) {
        String key = consumerRecord.key();
        Message message = consumerRecord.value();
        log.info("Avro message received for key : {} value : {}", key, message.toString());

    }

}
