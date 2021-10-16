package com.daw.proyecto;

import com.daw.proyecto.model.dto.ColoniaDTO;
import com.daw.proyecto.service.AlimentacionService;
import com.daw.proyecto.service.ColoniaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProyectoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(ColoniaService coloniaService, AlimentacionService alimentacionService) {
        return (String... args) -> {

            var colonia = ColoniaDTO.builder()
                    .longitud(10.0)
                    .latitud(10.0)
                    .numGatos(1)
                    .registro(false).build();
            coloniaService.saveColonia(colonia);

            var colonia2 = ColoniaDTO.builder()
                    .longitud(20.0)
                    .latitud(12.0)
                    .numGatos(5)
                    .registro(true).build();

            coloniaService.saveColonia(colonia2);


        };
    }
}
