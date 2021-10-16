package com.daw.proyecto.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {


    @Schema(description = "Código de error", example = "404")
    private Integer codigo;


    @Schema(description = "Descripción de error", example = "Not found")
    private String mensaje;


    @Schema(description = "Nivel de error", example = "ERROR")
    private String nivel;
}
