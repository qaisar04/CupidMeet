package kz.baltabayev.event.listener.extension.config;

import kz.baltabayev.event.listener.extension.model.dto.AuditEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import kz.baltabayev.event.listener.extension.utils.PropertiesUtil;

import java.util.Properties;

/**
 * KafkaProducerCreator is a configuration class for setting up a Kafka producer.
 * It uses properties defined in a properties file to configure the Kafka producer.
 */
public class KafkaProducerCreator {

  private static final String KAFKA_SERVER = "kafka.server";
  private static final String KAFKA_SERIALIZATION_KEY = "kafka.serializer.key";
  private static final String KAFKA_SERIALIZATION_VALUE = "kafka.serializer.value";

  /**
   * Creates a Kafka producer with properties defined in a properties file.
   * The properties file should contain the Kafka server address, key serializer, and value serializer.
   *
   * @return a Kafka producer configured with the properties defined in the properties file
   */
  public static Producer<String, AuditEvent> createProducer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", PropertiesUtil.get(KAFKA_SERVER));
    props.put("key.serializer", PropertiesUtil.get(KAFKA_SERIALIZATION_KEY));
    props.put("value.serializer", PropertiesUtil.get(KAFKA_SERIALIZATION_VALUE));
    return new KafkaProducer<>(props);
  }
}