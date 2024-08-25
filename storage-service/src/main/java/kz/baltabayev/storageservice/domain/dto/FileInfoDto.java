package kz.baltabayev.storageservice.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {
    @Valid
    private UUID id;
    @NotNull
    private String path;
    private String name;
    private String contentType;
}