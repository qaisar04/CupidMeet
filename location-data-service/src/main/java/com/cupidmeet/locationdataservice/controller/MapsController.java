package com.cupidmeet.locationdataservice.controller;

import com.cupidmeet.locationdataservice.model.dto.DistanceResponse;
import com.cupidmeet.locationdataservice.model.dto.LocationResponse;
import com.cupidmeet.locationdataservice.service.MapsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/maps")
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

    @Operation(operationId = "city exists   ", summary = "Проверка является ли city городом с популяцией больше 5000")
    @GetMapping("/city/exists")
    public ResponseEntity<Void> isCityExist(@RequestBody String city){
        mapsService.isCityValid(city);
        return ResponseEntity.ok().build();
    }
}