package com.daw.proyecto.repository;

import com.daw.proyecto.model.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {
}
