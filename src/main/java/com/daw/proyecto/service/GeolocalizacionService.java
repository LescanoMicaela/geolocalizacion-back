package com.daw.proyecto.service;

import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.dto.GeolocalizacionDTO;

import java.util.List;
import java.util.Optional;

public interface GeolocalizacionService {

    Geolocalizacion saveGeolocalizacion(Geolocalizacion geo);

    List<GeolocalizacionDTO> getGeolocalizacion();

    Optional<Geolocalizacion> getLocalizacion(double latitud, double longitud);
}
