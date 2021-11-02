package com.daw.proyecto;

import com.daw.proyecto.model.dto.request.ColonyRequest;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.service.FeedingService;
import com.daw.proyecto.service.ColonyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner initData(ColonyService colonyService, FeedingService feedingService,
                                      UserRepository repo, PasswordEncoder passwordEncoder) {
        return (String... args) -> {

//            repo.saveAndFlush(Usuario.builder()
//                    .email("email@email.com")
//                    .password(passwordEncoder.encode("pass"))
//                    .roles("USER")
//                    .enabled(true).build());

            var colonia = ColonyRequest.builder()
                    .lng(-3.70860714415001)
                    .lat(40.384102198911044)
                    .cats(1)
                    .register(false).build();
            colonyService.saveColony(colonia);

            var colonia2 = ColonyRequest.builder()
                    .lng(-3.7086500594926277)
                    .lat(40.38407768186082)
                    .cats(5)
                    .register(true).build();

            colonyService.saveColony(colonia2);

            var colonia4 = ColonyRequest.builder()
                    .lng(-3.70866000)
                    .lat(40.38408000)
                    .cats(5)
                    .register(true).build();

            colonyService.saveColony(colonia4);

            var colonia3 = ColonyRequest.builder()
                    .lng(-3.708650060)
                    .lat(40.384088)
                    .cats(5)
                    .register(true).build();

            colonyService.saveColony(colonia3);


        };
    }


}
