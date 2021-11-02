package com.daw.proyecto.service;

import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.dto.response.GeolocationResponse;

import java.util.List;
import java.util.Optional;

public interface GeolocationService {

    Geolocation saveGeolocation(Geolocation geo);

    List<GeolocationResponse> getLocation();

    Optional<Geolocation> getLocation(double lat, double lng);
}
