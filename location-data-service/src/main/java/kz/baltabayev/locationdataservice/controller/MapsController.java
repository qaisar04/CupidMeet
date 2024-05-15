package kz.baltabayev.locationdataservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get location data by coordinates", description = "Retrieves location information based on the provided latitude and longitude.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved location information", content = @Content(schema = @Schema(implementation = LocationInfo.class))),
            @ApiResponse(responseCode = "400", description = "Invalid coordinates", content = @Content)
    })
    @GetMapping("/geocode")
    public ResponseEntity<LocationInfo> geocode(
            @RequestParam String latitude,
            @RequestParam String longitude
    ) {
        LocationInfo locationInfo = mapsService.geocode(latitude, longitude);
        return ResponseEntity.ok(locationInfo);
    }

    @Operation(summary = "Calculate Haversine distance", description = "Calculates the Haversine distance between two points specified by their latitude and longitude.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated distance", content = @Content(schema = @Schema(implementation = DistanceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid coordinates", content = @Content)
    })
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