package com.cupidmeet.locationdataservice.service.impl;

import com.cupidmeet.locationdataservice.exception.LocationServiceException;
import com.cupidmeet.locationdataservice.model.dto.DistanceResponse;
import com.cupidmeet.locationdataservice.model.dto.LocationResponse;
import com.cupidmeet.locationdataservice.service.MapsService;
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

    private static final Integer EARTH_RADIUS = 6371;
    private static final String LOCATION_IQ_URL = "https://us1.locationiq.com/v1/reverse?key=%s&lat=%s&lon=%s&format=json";

    @Bean
    public RestTemplate restTemplate() {
        return restTemplate = new RestTemplate();
    }

    /**
     * {@inheritDoc}
     */
    public LocationResponse geocode(String latitude, String longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Широта и долгота не должны быть null");
        }

        String url = String.format(LOCATION_IQ_URL, API_KEY, latitude, longitude);
        try {
            log.info("Requesting location for lat: {}, lon: {}", latitude, longitude);
            LocationResponse response = restTemplate.getForEntity(url, LocationResponse.class).getBody();
            if (response == null) {
                throw new IllegalStateException("Нет ответа от службы определения местоположения");
            }
            return response;
        } catch (RestClientException e) {
            log.error("Ошибка при вызове Location IQ API: {}", e.getMessage());
            throw new LocationServiceException("Ошибка при вызове Location IQ API", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistanceResponse haversine(double lat1, double lon1, double lat2, double lon2) {
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        // The Haversine formula
        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return new DistanceResponse((double) Math.round(EARTH_RADIUS * c * 10) / 10);
    }
}