package com.daw.proyecto.repository;

import com.daw.proyecto.security.enums.ERole;
import com.daw.proyecto.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Role> findByName(ERole name);
}
