package com.daw.proyecto.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeolocalizacionDTO {

    @Schema(description = "Longitud de la geolocalizacion", example = "10.0")
    double longitud;

    @Schema(description = "Latitud de la geolocalizacion", example = "10.0")
    double latitud;
}
