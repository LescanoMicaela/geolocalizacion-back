package com.daw.proyecto.model.id;

import com.daw.proyecto.model.Colony;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.Instant;

/**
 * The type Feeding id.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class FeedingId implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The Colony.
     */
    @ManyToOne
    Colony colony;

    private Instant time;

}
