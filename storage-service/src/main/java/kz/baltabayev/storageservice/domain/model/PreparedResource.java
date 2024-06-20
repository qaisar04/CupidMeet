package kz.baltabayev.storageservice.domain.model;

import kz.baltabayev.storageservice.domain.entity.FileInfo;
import org.springframework.core.io.Resource;

/**
 * Represents a prepared resource containing both the resource and its associated file information.
 */
public record PreparedResource(Resource resource, FileInfo fileInfo) {
}