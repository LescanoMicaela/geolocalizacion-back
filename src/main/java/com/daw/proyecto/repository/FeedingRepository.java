package com.daw.proyecto.repository;

import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.id.FeedingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Feeding repository.
 */
@Repository
public interface FeedingRepository extends JpaRepository<Feeding, FeedingId> {

    /**
     * Find by id colony list.
     *
     * @param colony the colony
     * @return the list
     */
    List<Feeding> findByIdColony(Colony colony);

    /**
     * Find first by id colony order by time desc optional.
     *
     * @param colony the colony
     * @return the optional
     */
    Optional<Feeding> findFirstByIdColonyOrderByTimeDesc(Colony colony);
}
