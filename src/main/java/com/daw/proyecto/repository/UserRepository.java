package com.daw.proyecto.repository;

import com.daw.proyecto.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Exists by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    boolean existsByUsername(String username);

    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);
}
