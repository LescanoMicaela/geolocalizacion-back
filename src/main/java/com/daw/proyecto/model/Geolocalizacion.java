package com.daw.proyecto.model;

import com.daw.proyecto.model.id.GeolocalizacionId;
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
public class Geolocalizacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GeolocalizacionId id;

    private double zoom;
}
