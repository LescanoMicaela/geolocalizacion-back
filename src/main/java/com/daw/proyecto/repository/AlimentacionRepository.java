package com.daw.proyecto.repository;

import com.daw.proyecto.model.Alimentacion;
import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.id.AlimentacionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlimentacionRepository extends JpaRepository<Alimentacion, AlimentacionId> {

    List<Alimentacion> findByIdColonia(Colonia colonia);
}
