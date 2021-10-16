package com.daw.proyecto.model.dto;

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
public class AlimentacionDTO {

    @Schema(description = "Indica si hay agua", example = "true")
    private boolean hayAgua;

    @Schema(description = "Indica si hay comida", example = "true")
    private boolean hayComida;

    @Schema(description = "Indica si se ha proporcionado agua", example = "false")
    private boolean agua;

    @Schema(description = "Indica si se ha proporcionado comida", example = "true")
    private boolean comida;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Madrid")
    @Schema(description = "Fecha y hora de la alimentacion", example = "01-01-2021 10:30")
    private Instant fecha;


}
