package kz.baltabayev.storageservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Запрос на удаление файлов")
public class DeleteFileRequest {

    @Schema(description = "Список идентификаторов файлов для удаления")
    private List<UUID> filesId;
}