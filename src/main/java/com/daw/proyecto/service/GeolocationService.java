package com.daw.proyecto.service;

import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.response.GeolocationResponse;

import java.util.List;
import java.util.Optional;

/**
 * The interface Geolocation service.
 */
public interface GeolocationService {

    /**
     * Save geolocation geolocation.
     *
     * @param geo the geo
     * @return the geolocation
     */
    Geolocation saveGeolocation(Geolocation geo);

    /**
     * Gets location.
     *
     * @return the location
     */
    List<GeolocationResponse> getLocation();

    /**
     * Gets location.
     *
     * @param lat the lat
     * @param lng the lng
     * @return the location
     */
    Optional<Geolocation> getLocation(double lat, double lng);
}
