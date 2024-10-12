package kz.baltabayev.storageservice.config;

import kz.baltabayev.storageservice.domain.enumeration.S3Bucket;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("buckets")
public class BucketProperties {

    /**
     * Название бакета для хранения основных файлов.
     */
    private String cupid;
    /**
     * Название бакета для хранения файлов для QR кодов.
     */
    private String qr;

    /**
     * Получение названия бакета по типу.
     *
     * @param bucket тип бакета.
     * @return название бакета.
     */
    public String getBucketName(S3Bucket bucket) {
        return switch (bucket) {
            case MAIN -> cupid;
            case QR -> qr;
            default -> throw new IllegalArgumentException("Неопределенный тип бакета: " + bucket);
        };
    }
}