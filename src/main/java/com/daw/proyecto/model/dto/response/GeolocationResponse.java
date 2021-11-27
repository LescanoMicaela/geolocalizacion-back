package com.daw.proyecto.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * The type Geolocation response.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeolocationResponse {

    @Schema(description = "Longitude of the location", example = "10.00")
    private double lng;

    @Schema(description = "Latitude of th elocation", example = "10.00")
    private double lat;
}
