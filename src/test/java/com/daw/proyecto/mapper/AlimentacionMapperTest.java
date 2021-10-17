package com.daw.proyecto.mapper;

import com.daw.proyecto.model.Alimentacion;
import com.daw.proyecto.model.Colonia;
import com.daw.proyecto.model.dto.request.AlimentacionRequestDTO;
import com.daw.proyecto.model.dto.response.AlimentacionDTO;
import com.daw.proyecto.model.id.AlimentacionId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class AlimentacionMapperTest {

    private AlimentacionMapper mapper;

    private AlimentacionDTO alimentacionDTO;
    private AlimentacionRequestDTO alimentacionRequestDTO;
    private Alimentacion alimentacion;

    @Before
    public void setUp() {
        mapper = new AlimentacionMapper();
        alimentacionDTO = AlimentacionDTO.builder()
                .fecha(Instant.now())
                .build();

        alimentacionRequestDTO = AlimentacionRequestDTO.builder()
                .build();

        alimentacion = Alimentacion.builder()
                .id(AlimentacionId.builder()
                        .fecha(Instant.now())
                        .colonia(Colonia.builder().build())
                        .build())
                .build();
    }

    @Test
    public void entityToAlimentacionDTO() {
        var actual = mapper.entityToAlimentacionDTO(alimentacion);
        assertNotNull(actual);
        assertEquals(alimentacionDTO, actual);
    }

    @Test
    public void alimentacionDTOToEntity() {
        var actual = mapper.alimentacionDTOToEntity(alimentacionRequestDTO);
        assertNotNull(actual);
        assertEquals(alimentacion.isAgua(), actual.isAgua());
        assertEquals(alimentacion.isHayAgua(), actual.isHayAgua());
        assertEquals(alimentacion.isComida(), actual.isComida());
        assertEquals(alimentacion.isHayComida(), actual.isHayComida());

    }
}