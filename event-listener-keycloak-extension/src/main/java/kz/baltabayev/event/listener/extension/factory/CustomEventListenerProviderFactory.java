package kz.baltabayev.event.listener.extension.factory;

import kz.baltabayev.event.listener.extension.CustomEventListenerProvider;
import kz.baltabayev.event.listener.extension.config.KafkaProducerCreator;
import lombok.extern.slf4j.Slf4j;
import kz.baltabayev.event.listener.extension.model.dto.AuditEvent;
import org.apache.kafka.clients.producer.Producer;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * CustomEventListenerProviderFactory is a factory class for creating instances of
 * kz.baltabayev.event.listener.extension.CustomEventListenerProvider. It implements the EventListenerProviderFactory interface from
 * Keycloak. It uses a Kafka producer for sending events.
 */
@Slf4j
public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

  private static final String LISTENER_ID = "event-listener-extension";
  private Producer<String, AuditEvent> producer;

  /**
   * Creates a new instance of kz.baltabayev.event.listener.extension.CustomEventListenerProvider using the Kafka producer.
   *
   * @param session the Keycloak session
   * @return a new instance of kz.baltabayev.event.listener.extension.CustomEventListenerProvider
   */
  @Override
  public EventListenerProvider create(KeycloakSession session) {
    return new CustomEventListenerProvider(producer);
  }

  /**
   * Initializes the Kafka producer.
   *
   * @param scope the configuration scope
   */
  @Override
  public void init(Config.Scope scope) {
    log.info("start initializing kafka producer");
    producer = KafkaProducerCreator.createProducer();
    log.info("initialize kafka producer, {}", producer);
  }

  /**
   * Post-initialization method. Currently, does nothing.
   *
   * @param keycloakSessionFactory the Keycloak session factory
   */
  @Override
  public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
  }

  /**
   * Closes the Kafka producer when the factory is closed.
   */
  @Override
  public void close() {
    producer.close();
    log.debug("producer closed");
  }

  /**
   * Returns the ID of the listener.
   *
   * @return the ID of the listener
   */
  @Override
  public String getId() {
    return LISTENER_ID;
  }
}