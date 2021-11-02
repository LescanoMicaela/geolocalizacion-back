package com.daw.proyecto.model;

import com.daw.proyecto.model.id.FeedingId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Feeding implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    FeedingId id;

    private boolean water;
    private boolean food;
    private boolean waterAvailable;
    private boolean foodAvailable;


}
