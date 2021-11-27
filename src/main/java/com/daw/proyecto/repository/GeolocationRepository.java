package com.daw.proyecto.repository;

import com.daw.proyecto.model.Geolocation;
import com.daw.proyecto.model.id.GeolocationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Geolocation repository.
 */
@Repository
public interface GeolocationRepository extends JpaRepository<Geolocation, GeolocationId> {

}
