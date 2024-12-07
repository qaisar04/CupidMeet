package com.cupidmeet.event.listener.extension.factory;

import com.cupidmeet.event.listener.extension.CustomEventListenerProvider;
import com.cupidmeet.event.listener.extension.config.KafkaProducerCreator;
import cupid.meet.event.listener.extension.v0.AuditEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * Фабрика для создания экземпляров CustomEventListenerProvider.
 *
 * @author Kaisar Baltabayev.
 */
@Slf4j
public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

    // Идентификатор провайдера слушателя событий
    private static final String LISTENER_ID = "event-listener-extension";

    // Kafka-продюсер для отправки событий
    private Producer<String, AuditEvent> producer;

    /**
     * Создает новый экземпляр CustomEventListenerProvider, используя Kafka-продюсер.
     *
     * @param session текущая сессия Keycloak
     * @return новый экземпляр CustomEventListenerProvider
     */
    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new CustomEventListenerProvider(producer);
    }

    /**
     * Инициализирует Kafka-продюсер.
     * Вызывается при старте фабрики.
     *
     * @param scope объект конфигурации Keycloak
     */
    @Override
    public void init(Config.Scope scope) {
        log.info("start initializing kafka producer");
        producer = KafkaProducerCreator.createProducer();
        log.info("initialize kafka producer, {}", producer);
    }

    /**
     * Метод для выполнения действий после инициализации.
     * В текущей реализации ничего не выполняет.
     *
     * @param keycloakSessionFactory фабрика сессий Keycloak
     */
    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    /**
     * Закрывает Kafka-продюсер при завершении работы фабрики.
     */
    @Override
    public void close() {
        producer.close();
        log.debug("producer closed");
    }

    /**
     * Возвращает уникальный идентификатор провайдера слушателя событий.
     *
     * @return идентификатор провайдера
     */
    @Override
    public String getId() {
        return LISTENER_ID;
    }
}