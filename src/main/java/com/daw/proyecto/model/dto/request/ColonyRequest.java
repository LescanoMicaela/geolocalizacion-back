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
public class ColonyRequest {


    @Schema(description = "Number of cats in the colony", example = "5")
    private int cats;

    @Schema(description = "Indicates if the colony is regostered", example = "true")
    private boolean register;

    @Schema(description = "Longitude of the location", example = "10.00")
    private double lng;

    @Schema(description = "Latitude of th elocation", example = "10.00")
    private double lat;

}
