package com.cupidmeet.locationdataservice.controller;

import com.cupidmeet.locationdataservice.model.dto.DistanceResponse;
import com.cupidmeet.locationdataservice.model.dto.LocationResponse;
import com.cupidmeet.locationdataservice.service.MapsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/maps")
@RequiredArgsConstructor
public class MapsController {

    private final MapsService mapsService;

    @Operation(operationId = "geocode", summary = "Получение данных о местоположении по координатам")
    @GetMapping("/geocode")
    public ResponseEntity<LocationResponse> geocode(
            @RequestParam String latitude,
            @RequestParam String longitude
    ) {
        LocationResponse geocode = mapsService.geocode(latitude, longitude);
        return ResponseEntity.ok(geocode);
    }

    @Operation(operationId = "haversine", summary = "Вычислить поперечное расстояние")
    @GetMapping("/haversine")
    public ResponseEntity<DistanceResponse> haversine(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2
    ) {
        DistanceResponse response = mapsService.haversine(lat1, lon1, lat2, lon2);
        return ResponseEntity.ok(response);
    }
}