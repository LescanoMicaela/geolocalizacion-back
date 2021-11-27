package com.daw.proyecto.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


/**
 * The type Error dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {


    @Schema(description = "Error code", example = "404")
    private Integer code;


    @Schema(description = "Error description", example = "Not found")
    private String message;


    @Schema(description = "Error level", example = "ERROR")
    private String level;
}
