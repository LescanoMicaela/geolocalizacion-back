package com.daw.proyecto;

import com.daw.proyecto.model.dto.request.ColoniaRequestDTO;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.service.AlimentacionService;
import com.daw.proyecto.service.ColoniaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProyectoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(ColoniaService coloniaService, AlimentacionService alimentacionService,
                                      UserRepository repo, PasswordEncoder passwordEncoder) {
        return (String... args) -> {

//            repo.saveAndFlush(Usuario.builder()
//                    .email("email@email.com")
//                    .password(passwordEncoder.encode("pass"))
//                    .roles("USER")
//                    .enabled(true).build());

            var colonia = ColoniaRequestDTO.builder()
                    .longitud(-3.70860714415001)
                    .latitud(40.384102198911044)
                    .numGatos(1)
                    .registro(false).build();
            coloniaService.saveColonia(colonia);

            var colonia2 = ColoniaRequestDTO.builder()
                    .longitud(-3.7086500594926277)
                    .latitud(40.38407768186082)
                    .numGatos(5)
                    .registro(true).build();

            coloniaService.saveColonia(colonia2);


        };
    }


}
