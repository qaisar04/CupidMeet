package com.cupidmeet.locationdataservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistanceResponse {

    @Schema(description = "Растояние в км между двумя координатами")
    private Double km;
}