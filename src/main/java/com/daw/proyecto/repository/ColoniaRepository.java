package com.daw.proyecto.repository;

import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.Geolocalizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {

    Optional<Colonia> findByLocalizacion(Geolocalizacion geo);
}
