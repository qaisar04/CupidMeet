package com.cupidmeet.event.listener.extension;

import com.cupidmeet.event.listener.extension.utils.PropertiesUtil;
import cupid.meet.event.listener.extension.v0.AuditEvent;
import cupid.meet.event.listener.extension.v0.Payload;
import cupid.meet.event.listener.extension.v0.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.AuthDetails;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Реализация EventListenerProvider для отправки событий Keycloak в Kafka.
 * Поддерживает пользовательские и административные события.
 */
@Slf4j
public class CustomEventListenerProvider implements EventListenerProvider {

    private final Producer<String, AuditEvent> producer;

    private static final String KAFKA_TOPIC = "kafka.audit.topic.name";
    public static final DateTimeFormatter ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").withZone(
            ZoneId.systemDefault()
    );

    /**
     * Конструктор. Инициализирует Kafka-продюсер.
     *
     * @param producer Kafka-продюсер для отправки сообщений
     */
    public CustomEventListenerProvider(Producer<String, AuditEvent> producer) {
        this.producer = producer;
        log.info("Инициализирован CustomEventListenerProvider");
    }

    /**
     * Обрабатывает пользовательские события Keycloak и отправляет их в Kafka.
     *
     * @param event событие Keycloak
     */
    @Override
    public void onEvent(Event event) {
        log.info("Получено событие пользователя с userId: {}", event.getUserId());

        Instant startTime = Instant.ofEpochMilli(event.getTime());
        Instant endTime = Instant.now();

        Payload payload = Payload.newBuilder()
                .setUserId(event.getUserId())
                .setAction(event.getType().name())
                .setStartTime(formatTimestamp(startTime))
                .setEndTime(formatTimestamp(endTime))
                .setStatus(event.getError() == null ? Status.SUCCESS : Status.FAIL)
                .build();

        if (event.getIpAddress() != null) {
            payload.setIpAddress(event.getIpAddress());
        }

        AuditEvent auditEvent = AuditEvent.newBuilder()
                .setTimestamp(ZonedDateTime.now().format(ZONED_DATE_TIME_FORMATTER))
                .setPayload(payload)
                .build();

        sendMessageToKafka(auditEvent);
    }

    /**
     * Обрабатывает административные события Keycloak и отправляет их в Kafka.
     *
     * @param event событие администратора
     * @param includeRepresentation дополнительный параметр (не используется)
     */
    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        log.info("Получено административное событие с adminId: {}", event.getId());

        AuthDetails authDetails = event.getAuthDetails();

        Instant startTime = Instant.ofEpochMilli(event.getTime());
        Instant endTime = Instant.now();

        Payload payload = Payload.newBuilder()
                .setUserId(authDetails.getUserId())
                .setAction(event.getOperationType().name())
                .setStartTime(formatTimestamp(startTime))
                .setEndTime(formatTimestamp(endTime))
                .setEndTime(LocalDateTime.now().toString())
                .setStatus(event.getError() == null ? Status.SUCCESS : Status.FAIL)
                .build();

        if (authDetails.getIpAddress() != null) {
            payload.setIpAddress(authDetails.getIpAddress());
        }

        AuditEvent message = AuditEvent.newBuilder()
                .setTimestamp(ZonedDateTime.now().format(ZONED_DATE_TIME_FORMATTER))
                .setPayload(payload)
                .build();

        sendMessageToKafka(message);
    }

    /**
     * Закрывает провайдер событий.
     */
    @Override
    public void close() {
        log.info("Закрытие CustomEventListenerProvider");
    }

    /**
     * Отправляет объект AuditEvent в Kafka.
     *
     * @param auditEvent объект события для отправки
     */
    private void sendMessageToKafka(AuditEvent auditEvent) {
        ProducerRecord<String, AuditEvent> record = new ProducerRecord<>(
                PropertiesUtil.get(KAFKA_TOPIC), UUID.randomUUID().toString(), auditEvent);
        log.debug("Создан ProducerRecord: {}", record);
        producer.send(record);
        log.debug("Сообщение отправлено в Kafka: record={}, auditEvent={}", record, auditEvent);
    }

    /**
     * Форматирует LocalDateTime в ISO-формат с миллисекундами и временной зоной.
     */
    private static String formatTimestamp(Instant instant) {
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}