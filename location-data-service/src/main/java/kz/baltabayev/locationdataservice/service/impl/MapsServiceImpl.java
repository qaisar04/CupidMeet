package kz.baltabayev.locationdataservice.service.impl;

import kz.baltabayev.locationdataservice.exception.LocationServiceException;
import kz.baltabayev.locationdataservice.model.dto.DistanceResponse;
import kz.baltabayev.locationdataservice.model.dto.LocationResponse;
import kz.baltabayev.locationdataservice.model.payload.LocationInfo;
import kz.baltabayev.locationdataservice.service.MapsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapsServiceImpl implements MapsService {

    @Value("${locationiq.api-key}")
    private String API_KEY;

    private RestTemplate restTemplate;
    private final Integer EARTH_RADIUS = 6371;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplate = new RestTemplate();
    }

    public LocationInfo geocode(String latitude, String longitude) {
        if (latitude == null || longitude == null) {
            log.error("Latitude and longitude must not be null");
            throw new IllegalArgumentException("Latitude and longitude must not be null");
        }

        String url = String.format("https://us1.locationiq.com/v1/reverse?key=%s&lat=%s&lon=%s&format=json", API_KEY, latitude, longitude);
        try {
            log.info("Requesting location for lat: {}, lon: {}", latitude, longitude);
            LocationResponse response = restTemplate.getForEntity(url, LocationResponse.class).getBody();
            if (response == null) {
                log.error("No response from location service");
                throw new IllegalStateException("No response from location service");
            }
            return parseLocation(response);
        } catch (RestClientException e) {
            log.error("Error during calling Location IQ API: {}", e.getMessage());
            throw new LocationServiceException("Error when calling Location IQ API", e);
        }
    }

    @Override
    public DistanceResponse calculateDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        return new DistanceResponse(distance);
    }

    private LocationInfo parseLocation(LocationResponse response) {
        if (response.getAddress() == null) {
            log.error("Address is null in the response");
            throw new IllegalStateException("Address is null in the response");
        }
        return new LocationInfo(response.getAddress().getCity());
    }
}