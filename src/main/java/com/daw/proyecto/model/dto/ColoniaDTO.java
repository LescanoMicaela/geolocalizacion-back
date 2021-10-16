package com.daw.proyecto.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColoniaDTO {


    private long id;

    private int numGatos;

    private Long registro;

    private double longitud;

    private double latitud;

    private double zoom;


}
