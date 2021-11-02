package com.daw.proyecto.model.id;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class GeolocationId implements Serializable {

    private double lat;

    private double lng;


}
