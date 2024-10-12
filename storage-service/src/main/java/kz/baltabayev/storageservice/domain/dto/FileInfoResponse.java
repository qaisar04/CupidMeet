package kz.baltabayev.storageservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Информация о файле")
public class FileInfoResponse {

    @Schema(description = "Идентификатор файла")
    private UUID id;

    @NotNull
    @Schema(description = "Путь к файлу")
    private String path;

    @Schema(description = "Имя файла")
    private String name;

    @Schema(description = "Расширение файла")
    private String contentType;
}