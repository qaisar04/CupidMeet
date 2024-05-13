package kz.baltabayev.locationdataservice.controller;

import kz.baltabayev.locationdataservice.model.dto.DistanceResponse;
import kz.baltabayev.locationdataservice.model.payload.LocationInfo;
import kz.baltabayev.locationdataservice.service.MapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/maps")
@RequiredArgsConstructor
public class MapsController {

    private final MapsService mapsService;

    @GetMapping("/geocode")
    public ResponseEntity<LocationInfo> geocode(
            @RequestParam String latitude,
            @RequestParam String longitude
    ) {
        LocationInfo locationInfo = mapsService.geocode(latitude, longitude);
        return ResponseEntity.ok(locationInfo);
    }

    @GetMapping("/haversine")
    public ResponseEntity<DistanceResponse> haversine(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2
    ) {
        DistanceResponse response = mapsService.calculateDistanceInMeters(lat1, lon1, lat2, lon2);
        return ResponseEntity.ok(response);
    }
}