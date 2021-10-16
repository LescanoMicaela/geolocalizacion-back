package com.daw.proyecto.repository;

import com.daw.proyecto.model.Alimentacion;
import com.daw.proyecto.model.id.AlimentacionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentacionRepository extends JpaRepository<Alimentacion, AlimentacionId> {
}
