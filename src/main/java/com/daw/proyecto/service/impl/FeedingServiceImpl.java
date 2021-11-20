package com.daw.proyecto.service.impl;

import com.daw.proyecto.exception.EntityNotSavedException;
import com.daw.proyecto.exception.ResourceNotFoundException;
import com.daw.proyecto.mapper.FeedingMapper;
import com.daw.proyecto.model.dto.request.FeedingRequest;
import com.daw.proyecto.model.dto.response.FeedingResponse;
import com.daw.proyecto.model.id.FeedingId;
import com.daw.proyecto.repository.FeedingRepository;
import com.daw.proyecto.repository.UserRepository;
import com.daw.proyecto.security.model.User;
import com.daw.proyecto.security.model.UserDetailsImpl;
import com.daw.proyecto.service.ColonyService;
import com.daw.proyecto.service.FeedingService;
import com.daw.proyecto.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Servicio de Alimentacion
 * para recuperar, guardar
 * y modificar los datos de la entidad
 * Alimentacion
 */
@Service
@Slf4j
public class FeedingServiceImpl implements FeedingService {

    private final FeedingRepository repo;

    private final ColonyService colonyService;

    private final FeedingMapper mapper;

    private final UserRepository userRepo;


    public FeedingServiceImpl(FeedingRepository repo, ColonyService colonyService, FeedingMapper mapper, UserRepository userRepo) {
        this.repo = repo;
        this.colonyService = colonyService;
        this.mapper = mapper;
        this.userRepo = userRepo;
    }

    @Override
    public List<FeedingResponse> getColonyFeeding(Long colonyId) {
        var colonia = colonyService.getColony(colonyId);

        return repo.findByIdColony(colonia)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FeedingResponse saveFeeding(Long colonyId, FeedingRequest feeding) {
        var colony = colonyService.getColony(colonyId);

        return Stream.of(feeding)
                .map(mapper::dtoToEntity)
                .peek(a -> a.setId(FeedingId.builder()
                        .colony(colony)
                        .time(Instant.now())
                        .build()))
                .peek(a -> a.setCreateUser(getUser()))
                .map(repo::saveAndFlush)
                .map(mapper::entityToDto)
                .findFirst()
                .orElseThrow(() -> new EntityNotSavedException(Constants.FEEDING_NOT_SAVED));
    }


    private User getUser() {
        var username = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(s -> (UserDetailsImpl) s.getPrincipal())
                .map(UserDetailsImpl::getUsername)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.NO_USER_LOGGED));
        return userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(Constants.NO_USER_FOUND));
    }
}
