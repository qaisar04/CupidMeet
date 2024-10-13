package kz.baltabayev.storageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.baltabayev.storageservice.domain.dto.DeleteFileRequest;
import kz.baltabayev.storageservice.domain.dto.FileInfoResponse;
import kz.baltabayev.storageservice.domain.model.PreparedResource;
import kz.baltabayev.storageservice.facade.FileFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с файлами в хранилище.
 */
@Slf4j
@RestController
@RequestMapping("/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileFacade fileFacade;

    @Value("${contentDisposition}")
    private String contentDisposition;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно загружен",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)),
            @ApiResponse(responseCode = "404", description = "Файл не найден")
    })
    @GetMapping("/download")
    @Operation(summary = "Загрузить файл по пути")
    public ResponseEntity<Object> fileDownload(String path) {
        log.info("Received request downloading file with path : {}", path);
        PreparedResource preparedResource = fileFacade.getPreparedResourceByPath(path);
        String headerValue = contentDisposition + "; filename=\"" + preparedResource
                .resource()
                .getFilename() + "\"";
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(preparedResource.fileInfo().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(preparedResource.resource());
    }

    @Operation(summary = "Загружать несколько файлов")
    @ApiResponse(responseCode = "200", description = "Файлы успешно загружены",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FileInfoResponse.class)))
    @PostMapping("/upload/batch")
    public ResponseEntity<List<FileInfoResponse>> fileUploadBatch(@RequestBody List<MultipartFile> files) {
        log.info("[FileController] starts endpoint fileUploadBatch");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileFacade.saveFiles(files));
    }

    @Operation(summary = "Удалить файл по идентификатору")
    @ApiResponse(responseCode = "200", description = "Файл успешно удален")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID fileId) {
        fileFacade.deleteFileById(fileId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить файлы по списку идентификаторов")
    @ApiResponse(responseCode = "200", description = "Файлы успешно удалены")
    @DeleteMapping
    public ResponseEntity<Void> deleteFiles(@RequestBody DeleteFileRequest request) {
        fileFacade.addFilesToDelete(request.getFilesId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Загрузить файл")
    @ApiResponse(responseCode = "200", description = "Файл успешно загружен",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FileInfoResponse.class)))
    @PostMapping("/upload")
    public ResponseEntity<FileInfoResponse> fileUpload(@RequestPart(value = "file") MultipartFile file) {
        log.info("[FileController] starts endpoint fileUpload");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileFacade.saveFile(file));
    }
}