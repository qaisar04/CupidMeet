package kz.baltabayev.storageservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.baltabayev.storageservice.domain.dto.DeleteFileRequest;
import kz.baltabayev.storageservice.domain.dto.FileInfoDto;
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
 * Rest Controller for handling requests related to storage. Implements the FileApi interface, which
 * provides endpoints for saving and retrieving files and file's meta-info.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileFacade fileFacade;

    @Value("${contentDisposition}")
    private String contentDisposition;

    /**
     * Handles the file download API endpoint.
     *
     * @param path The path of the file to be downloaded.
     * @return The ResponseEntity with the file contents and appropriate headers.
     */
    @Operation(summary = "Download a file by path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/download")
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

    /**
     * Method provides saving the list of business idea's files uploaded by user.
     *
     * @param files List of business idea's files
     * @return List<FileInfoDto> Detailed information about saved files
     */
    @Operation(summary = "Upload multiple files")
    @ApiResponse(responseCode = "200", description = "Files uploaded successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FileInfoDto.class)))
    @PostMapping("/upload/batch")
    public ResponseEntity<List<FileInfoDto>> fileUploadBatch(@RequestBody List<MultipartFile> files) {
        log.info("[FileController] starts endpoint fileUploadBatch");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileFacade.saveFiles(files));
    }

    /**
     * Deletes a file with the specified ID.
     *
     * @param fileId the unique identifier of the file to be deleted.
     * @return ResponseEntity with an empty body and status 200 (OK) if the deletion is successful.
     */
    @Operation(summary = "Delete a file by ID")
    @ApiResponse(responseCode = "200", description = "File deleted successfully")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID fileId) {
        fileFacade.deleteFileById(fileId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes files based on the provided file IDs. This DELETE endpoint allows for the deletion of
     * files by accepting a request containing a list of file IDs. The files corresponding to the
     * provided IDs will be deleted. Method: DELETE Path: /api/v1/file
     *
     * @param request The request containing a list of file IDs to be deleted.
     * @return A ResponseEntity with status code 200 (OK) if the files are successfully deleted.
     */
    @Operation(summary = "Delete multiple files by IDs")
    @ApiResponse(responseCode = "200", description = "Files deleted successfully")
    @DeleteMapping
    public ResponseEntity<Void> deleteFiles(@RequestBody DeleteFileRequest request) {
        fileFacade.addFilesToDelete(request.getFilesId());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for uploading a file.
     *
     * @param file the file to be uploaded
     * @return a ResponseEntity containing the information of the saved file
     */
    @Operation(summary = "Upload a file")
    @ApiResponse(responseCode = "200", description = "File uploaded successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FileInfoDto.class)))
    @PostMapping("/upload")
    public ResponseEntity<FileInfoDto> fileUpload(@RequestPart(value = "file") MultipartFile file) {
        log.info("[FileController] starts endpoint fileUpload");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileFacade.saveFile(file));
    }
}