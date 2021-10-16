package com.daw.proyecto.model;

import com.daw.proyecto.model.id.AlimentacionId;
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
public class Alimentacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    AlimentacionId id;

    private boolean agua;
    private boolean comida;
    private boolean hayComida;
    private boolean hayAgua;


}
