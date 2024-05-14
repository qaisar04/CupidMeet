package kz.baltabayev.locationdataservice.service;

import kz.baltabayev.locationdataservice.model.dto.DistanceResponse;
import kz.baltabayev.locationdataservice.model.payload.LocationInfo;

public interface MapsService {

    LocationInfo geocode(String latitude, String longitude);

    DistanceResponse haversine(double lat1, double lon1, double lat2, double lon2);
}