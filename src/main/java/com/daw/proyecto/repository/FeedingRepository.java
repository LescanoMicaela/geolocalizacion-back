package com.daw.proyecto.repository;

import com.daw.proyecto.model.Feeding;
import com.daw.proyecto.model.Colony;
import com.daw.proyecto.model.id.FeedingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedingRepository extends JpaRepository<Feeding, FeedingId> {

    List<Feeding> findByIdColony(Colony colony);

    Optional<Feeding> findFirstByIdColonyOrderByTimeDesc(Colony colony);
}
