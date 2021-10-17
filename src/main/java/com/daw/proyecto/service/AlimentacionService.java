package com.daw.proyecto.service;

import com.daw.proyecto.model.dto.request.AlimentacionRequestDTO;
import com.daw.proyecto.model.dto.response.AlimentacionDTO;

import java.util.List;

public interface AlimentacionService {

    List<AlimentacionDTO> getAlimentacionColonia(Long coloniaId);

    AlimentacionDTO saveAlimentacion(Long coloniaId, AlimentacionRequestDTO alimentacionDTO);
}
