package com.cupidmeet.event.listener.extension.config;

import com.cupidmeet.event.listener.extension.utils.PropertiesUtil;
import cupid.meet.event.listener.extension.v0.AuditEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * Класс KafkaProducerCreator отвечает за создание конфигурации для Kafka-продюсера.
 * Использует параметры, указанные в конфигурационном файле, для настройки подключения к Kafka и сериализации данных.
 */
public class KafkaProducerCreator {

    private static final String KAFKA_BOOTSTRAP_SERVER = "bootstrap.servers";
    private static final String SCHEMA_REGISTRY_URL = "schema.registry.url";
    private static final String KAFKA_SERIALIZATION_KEY = "kafka.serializer.key";
    private static final String KAFKA_SERIALIZATION_VALUE = "kafka.serializer.value";

    /**
     * Создает и настраивает продюсер Kafka для отправки сообщений.
     * Читает параметры подключения и сериализации из конфигурационного файла.
     *
     * @return объект Kafka-продюсера, настроенный для отправки сообщений
     */
    public static Producer<String, AuditEvent> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", PropertiesUtil.get(KAFKA_BOOTSTRAP_SERVER));
        props.put("key.serializer", PropertiesUtil.get(KAFKA_SERIALIZATION_KEY));
        props.put("value.serializer", PropertiesUtil.get(KAFKA_SERIALIZATION_VALUE));
        props.put("schema.registry.url", PropertiesUtil.get(SCHEMA_REGISTRY_URL));
        return new KafkaProducer<>(props);
    }
}