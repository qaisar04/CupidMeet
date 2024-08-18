package com.cupidmeet.locationdataservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс, представляющий ответ с данными о местоположении.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Ответ с данными о местоположении")
public class LocationResponse {

    @Schema(description = "Уникальный идентификатор места")
    private String place_id;

    @Schema(description = "Лицензия, связанная с данными")
    private String licence;

    @Schema(description = "Тип объекта в базе данных OpenStreetMap")
    private String osm_type;

    @Schema(description = "Идентификатор объекта в базе данных OpenStreetMap")
    private long osm_id;

    @Schema(description = "Широта местоположения")
    private String lat;

    @Schema(description = "Долгота местоположения")
    private String lon;

    @Schema(description = "Отображаемое имя местоположения")
    private String display_name;

    @Schema(description = "Адрес, связанный с местоположением")
    private Address address;

    @Schema(description = "Границы местоположения")
    private String[] boundingbox;
}
