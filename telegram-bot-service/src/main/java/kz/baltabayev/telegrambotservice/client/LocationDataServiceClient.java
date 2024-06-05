package kz.baltabayev.telegrambotservice.client;

import kz.baltabayev.telegrambotservice.model.payload.LocationInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "location-data-service", path = "api/v1/maps")
public interface LocationDataServiceClient {

    @GetMapping("/geocode")
    ResponseEntity<LocationInfo> geocode(
            @RequestParam String latitude,
            @RequestParam String longitude
    );
}