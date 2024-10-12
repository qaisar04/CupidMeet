package kz.baltabayev.storageservice.domain.model;

import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.springframework.core.io.Resource;

/**
 * Представляет собой подготовленный ресурс, содержащий как сам ресурс, так и информацию о связанном с ним файле.
 */
public record PreparedResource(
        Resource resource,
        FileInfo fileInfo
) {
}