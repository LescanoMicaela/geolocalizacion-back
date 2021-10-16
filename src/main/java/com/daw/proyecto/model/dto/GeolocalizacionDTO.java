package com.daw.proyecto.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeolocalizacionDTO {

    double longitud;
    double latitud;
    double zoom;
}
