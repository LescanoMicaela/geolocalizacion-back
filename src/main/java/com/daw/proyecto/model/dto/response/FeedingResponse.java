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
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class FeedingResponse {

    @Schema(description = "Water available in colony", example = "true")
    private boolean waterAvailable;

    @Schema(description = "Food available in colony", example = "true")
    private boolean foodAvailable;

    @Schema(description = "Water provided", example = "false")
    private boolean water;

    @Schema(description = "Food provided", example = "true")
    private boolean food;


    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Madrid")
    @Schema(description = "Date and time of the feeding", example = "01-01-2021 10:30")
    private Instant time;


}
