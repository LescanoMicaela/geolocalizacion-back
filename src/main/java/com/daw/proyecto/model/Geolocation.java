package com.daw.proyecto.model;

import com.daw.proyecto.model.id.GeolocationId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
public class Geolocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GeolocationId id;


}
