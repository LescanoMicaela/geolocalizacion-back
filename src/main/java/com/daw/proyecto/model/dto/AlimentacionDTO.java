package com.daw.proyecto.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlimentacionDTO {

    private boolean hayAgua;

    private boolean hayComida;

    private boolean agua;

    private boolean comida;

    private Instant fecha;


}
