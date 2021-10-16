package com.daw.proyecto.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlimentacionRequestDTO {

    @Schema(description = "Indica si hay agua", example = "true")
    private boolean hayAgua;

    @Schema(description = "Indica si hay comida", example = "true")
    private boolean hayComida;

    @Schema(description = "Indica si se ha proporcionado agua", example = "false")
    private boolean agua;

    @Schema(description = "Indica si se ha proporcionado comida", example = "true")
    private boolean comida;

}
