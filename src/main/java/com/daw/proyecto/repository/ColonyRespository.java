package com.daw.proyecto.repository;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Geolocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColonyRespository extends JpaRepository<Colony, Long> {

    Optional<Colony> findByLocation(Geolocation geo);
}
