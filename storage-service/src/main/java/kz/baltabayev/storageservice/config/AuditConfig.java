package kz.baltabayev.storageservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class enables Jpa Auditing
 */
@EnableJpaAuditing
@Configuration
public class AuditConfig {
}