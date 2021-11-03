package com.daw.proyecto.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColonyResponse {

    @Schema(description = "Id of the colony", example = "123")
    private long id;

    @Schema(description = "Number of cats in the colony", example = "5")
    private int cats;

    @Schema(description = "Indicates if the colony is regostered", example = "true")
    private boolean register;

    @Schema(description = "Longitude of the location", example = "10.00")
    private double lng;

    @Schema(description = "Latitude of th elocation", example = "10.00")
    private double lat;

    @Schema(description = "Food provided to the colony last feeding")
    Boolean food;

    @Schema(description = "Water provided to the colony last feeding")
    Boolean water;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Madrid")
    @Schema(description = "Date and time of the feeding", example = "01-01-2021 10:30")
    private Instant time;

}
