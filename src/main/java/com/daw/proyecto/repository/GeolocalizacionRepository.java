package com.daw.proyecto.repository;

import com.daw.proyecto.model.Geolocalizacion;
import com.daw.proyecto.model.id.GeolocalizacionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeolocalizacionRepository extends JpaRepository<Geolocalizacion, GeolocalizacionId> {

}
