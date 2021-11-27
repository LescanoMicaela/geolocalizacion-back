package com.daw.proyecto.repository;

import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.Geolocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Colony respository.
 */
@Repository
public interface ColonyRespository extends JpaRepository<Colony, Long> {

    /**
     * Find by location optional.
     *
     * @param geo the geo
     * @return the optional
     */
    Optional<Colony> findByLocation(Geolocation geo);
}
