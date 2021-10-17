package com.daw.proyecto.service;

import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.model.dto.response.ColoniaDTO;

import java.util.List;

public interface ColoniaService {

    List<ColoniaDTO> getColonias();

    Colonia getColonia(Long id);

    ColoniaDTO getColonia(double longitud, double latitud);

    ColoniaDTO saveColonia(ColoniaRequestDTO colonia);

    void deleteColonia(Long id);

    ColoniaDTO updateColonia(ColoniaDTO coloniaDTO);
}