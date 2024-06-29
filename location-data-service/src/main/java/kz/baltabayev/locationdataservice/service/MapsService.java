package kz.baltabayev.locationdataservice.service;

import kz.baltabayev.locationdataservice.model.dto.DistanceResponse;
import kz.baltabayev.locationdataservice.model.dto.LocationResponse;

/**
 * Interface for map-related services, providing methods for geocoding and calculating distances.
 */
public interface MapsService {

    /**
     * Geocodes the given latitude and longitude into a LocationInfo object.
     *
     * @param latitude the latitude to geocode
     * @param longitude the longitude to geocode
     * @return a LocationInfo object containing the geocoded information
     */
    LocationResponse geocode(String latitude, String longitude);

    /**
     * Calculates the Haversine distance between two points specified by their latitude and longitude.
     *
     * @param lat1 the latitude of the first point
     * @param lon1 the longitude of the first point
     * @param lat2 the latitude of the second point
     * @param lon2 the longitude of the second point
     * @return a DistanceResponse object containing the calculated distance
     */
    DistanceResponse haversine(double lat1, double lon1, double lat2, double lon2);
}