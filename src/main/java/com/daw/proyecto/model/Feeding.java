package com.daw.proyecto.model;

import com.daw.proyecto.model.id.FeedingId;
import com.daw.proyecto.security.model.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * The type Feeding.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Feeding implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The Id.
     */
    @EmbeddedId
    FeedingId id;

    private boolean water;
    private boolean food;
    private boolean waterAvailable;
    private boolean foodAvailable;

    @ManyToOne
    private User createUser;

    @Column(insertable = false, updatable = false)
    private Instant time;


}
