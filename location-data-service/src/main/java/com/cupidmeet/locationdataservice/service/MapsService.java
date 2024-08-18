package com.cupidmeet.locationdataservice.service;

import com.cupidmeet.locationdataservice.model.dto.DistanceResponse;
import com.cupidmeet.locationdataservice.model.dto.LocationResponse;

/**
 * Интерфейс для картографических сервисов, предоставляющий методы геокодирования и расчета расстояний.
 */
public interface MapsService {

    /**
     * Геокодирует заданные широту и долготу в объект LocationInfo.
     *
     * @param latitude широта для геокодирования
     * @param longitude долгота для геокодирования
     * @return обьект LocationInfo, содержащий геокодированную информацию
     */
    LocationResponse geocode(String latitude, String longitude);

    /**
     * Вычисляет поперечное расстояние между двумя точками, заданное их широтой и долготой.
     *
     * @param lat1 широта первой точки
     * @param lon1 долгота первой точки
     * @param lat2 широта второй точки
     * @param lon2 долгота второй точки
     * @return объект DistanceResponse, содержащий вычисленное расстояние
     */
    DistanceResponse haversine(double lat1, double lon1, double lat2, double lon2);
}