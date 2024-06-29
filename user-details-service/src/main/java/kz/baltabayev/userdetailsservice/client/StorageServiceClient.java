package kz.baltabayev.userdetailsservice.client;

import kz.baltabayev.userdetailsservice.model.payload.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "storage-service", path = "/api/v1/file", url = "${url.storage-service}")
public interface StorageServiceClient {

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileUploadResponse> upload(
            @RequestPart("file") MultipartFile file
    );

    @PostMapping(path = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<FileUploadResponse>> uploadBatch(
            @RequestBody List<MultipartFile> files
    );

    @DeleteMapping("/{fileId}")
    ResponseEntity<String> delete(
            @PathVariable UUID fileId
    );
}