package com.daw.proyecto.model.id;

import com.daw.proyecto.model.Colonia;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class AlimentacionId implements Serializable {

    private static final long serialVersionUID = 1L;


    @ManyToOne
    @JoinColumn(name = "id")
    Colonia colonia;

    private Instant fecha;

}
