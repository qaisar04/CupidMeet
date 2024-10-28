package io.ylab.event.listener.extension;

import io.ylab.event.listener.extension.model.dto.AuditEvent;
import io.ylab.event.listener.extension.model.type.Status;
import io.ylab.event.listener.extension.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * CustomEventListenerProvider is a class that implements the EventListenerProvider interface from Keycloak.
 * It listens for events and sends them to a Kafka topic as AuditEvent objects.
 * It uses a Kafka producer for sending the events.
 */
@Slf4j
public class CustomEventListenerProvider implements EventListenerProvider {

    private final Producer<String, AuditEvent> producer;
    private static final String KAFKA_TOPIC = "kafka.audit.topic.name";

    /**
     * Constructor for the CustomEventListenerProvider.
     * Initializes the Kafka producer.
     *
     * @param producer the Kafka producer
     */
    public CustomEventListenerProvider(Producer<String, AuditEvent> producer) {
        this.producer = producer;
        log.info("CustomEventListenerProvider created");
    }

    /**
     * Handles an event and sends it to the Kafka topic as an AuditEvent object.
     *
     * @param event the event to be handled
     */
    @Override
    public void onEvent(Event event) {
        log.info("Caught event with userId: {}", event.getUserId());

        AuditEvent auditEvent = AuditEvent.builder()
                .userUUID(event.getUserId())
                .action(event.getType().name())
                .startedAt(
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTime()), ZoneId.systemDefault())
                )
                .finishedAt(LocalDateTime.now())
                .build();

        auditEvent.setStatus(
                event.getError() == null
                        ? Status.SUCCESS : Status.FAIL
        );

        sendMessageToKafka(auditEvent);
    }

    /**
     * Handles an admin event and sends it to the Kafka topic as an AuditEvent object.
     *
     * @param adminEvent the admin event to be handled
     * @param b          a boolean value
     */
    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        log.info("Caught admin event with adminId: {}", adminEvent.getId());
        AuditEvent event = AuditEvent.builder()
                .userUUID(adminEvent.getId())
                .action(adminEvent.getOperationType().name())
                .startedAt(
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(adminEvent.getTime()),
                                ZoneId.systemDefault())
                )
                .finishedAt(LocalDateTime.now())
                .build();
        event.setStatus(
                adminEvent.getError() == null
                        ? Status.SUCCESS : Status.FAIL
        );

        sendMessageToKafka(event);
    }

    /**
     * Closes the EventListenerProvider. Currently, does nothing.
     */
    @Override
    public void close() {
    }

    /**
     * Sends an AuditEvent object to the Kafka topic.
     *
     * @param auditEvent the AuditEvent object to be sent
     */
    private void sendMessageToKafka(AuditEvent auditEvent) {
        ProducerRecord<String, AuditEvent> auditEventProducerRecord = new ProducerRecord<>(
                PropertiesUtil.get(KAFKA_TOPIC), UUID.randomUUID().toString(), auditEvent);
        log.debug("Created ProducerRecord: {}", auditEventProducerRecord);
        producer.send(auditEventProducerRecord);
        log.debug("Send message to Kafka (record: {}, auditEvent: {})", auditEventProducerRecord,
                auditEvent);
    }
}