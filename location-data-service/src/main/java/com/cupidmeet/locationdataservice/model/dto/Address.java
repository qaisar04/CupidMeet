package com.cupidmeet.locationdataservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс, представляющий адрес, связанный с местоположением.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Адрес, связанный с местоположением")
public class Address {

    @Schema(description = "Название университета")
    private String university;

    @Schema(description = "Название улицы")
    private String road;

    @Schema(description = "Название района")
    private String suburb;

    @Schema(description = "Название административного округа города")
    private String city_district;

    @Schema(description = "Название города")
    private String city;

    @Schema(description = "Почтовый индекс")
    private String postcode;

    @Schema(description = "Название страны")
    private String country;

    @Schema(description = "Код страны")
    private String country_code;
}