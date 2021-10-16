package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Alimentacion;
import com.daw.proyecto.model.dto.AlimentacionDTO;
import org.springframework.stereotype.Component;

@Component
public class AlimentacionMapper {

    public AlimentacionDTO entityToAlimentacionDTO(Alimentacion alimentacion) {
        return AlimentacionDTO.builder()
                .agua(alimentacion.isAgua())
                .comida(alimentacion.isComida())
                .hayAgua(alimentacion.isHayAgua())
                .hayComida(alimentacion.isHayComida())
                .fecha(alimentacion.getId().getFecha())
                .build();
    }

    public Alimentacion alimentacionDTOToEntity(AlimentacionDTO alimentacion) {
        return Alimentacion.builder()
                .agua(alimentacion.isAgua())
                .comida(alimentacion.isComida())
                .hayAgua(alimentacion.isHayAgua())
                .hayComida(alimentacion.isHayComida())
                .build();
    }
}
