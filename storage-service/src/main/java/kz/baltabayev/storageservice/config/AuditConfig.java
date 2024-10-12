package kz.baltabayev.storageservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Конфигурация аудита.
 */
@EnableJpaAuditing
@Configuration
public class AuditConfig {

}