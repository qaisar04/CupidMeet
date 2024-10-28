package io.ylab.event.listener.extension.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.ylab.event.listener.extension.model.dto.AuditEvent;
import org.apache.kafka.common.serialization.Serializer;

/**
 * AuditEventSerializer is a class that implements the Serializer interface from Apache Kafka.
 * It is used to serialize AuditEvent objects into a byte array for sending over Kafka.
 * It uses the Jackson library for serialization, with the JavaTimeModule to handle Java 8 date/time types.
 */
public class AuditEventSerializer implements Serializer<AuditEvent> {

    private final ObjectMapper objectMapper;

    /**
     * Constructor for the AuditEventSerializer.
     * Initializes the ObjectMapper and registers the JavaTimeModule to it.
     */
    public AuditEventSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Serializes an AuditEvent object into a byte array.
     * If an error occurs during serialization, it throws a RuntimeException.
     *
     * @param topic the topic associated with the data
     * @param data  the AuditEvent object to be serialized
     * @return the serialized AuditEvent object as a byte array
     */
    @Override
    public byte[] serialize(String topic, AuditEvent data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сериализации объекта AuditEvent", e);
        }
    }
}