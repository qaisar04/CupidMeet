package kz.baltabayev.storageservice.domain.enumeration;

/**
 * Перечисление для определения бакета S3.
 */
public enum S3Bucket {
    /**
     * Бакет для хранения основных файлов.
     */
    MAIN,
    /**
     * Бакет для хранения файлов для QR кодов.
     */
    QR
}
