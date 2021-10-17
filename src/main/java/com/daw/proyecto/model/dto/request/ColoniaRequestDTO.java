package com.daw.proyecto.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColoniaRequestDTO {


    @Schema(description = "Número de gatos que hay en la colonia", example = "5")
    private int numGatos;

    @Schema(description = "Indica si la colonia esta censada", example = "true")
    private boolean registro;

    @Schema(description = "Longitud de la geolocalizacion", example = "10.00")
    private double longitud;

    @Schema(description = "Latitud de la geolocalizacion", example = "10.00")
    private double latitud;

}
