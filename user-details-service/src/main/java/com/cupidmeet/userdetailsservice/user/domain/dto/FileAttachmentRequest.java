package com.cupidmeet.userdetailsservice.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class FileAttachmentRequest {

    @Schema(description = "Уникальный идентификатор файла, связанного с вложением.")
    private UUID fileId;

    @Schema(description = "Название файла во вложении.")
    private String name;

    @Schema(description = "Тип содержимого файла (например, 'image/jpeg', 'application/pdf').")
    private String contentType;

    @Schema(description = "Путь к файлу на сервере или в файловой системе.")
    private String path;
}