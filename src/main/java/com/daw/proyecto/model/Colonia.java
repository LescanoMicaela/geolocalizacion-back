package com.daw.proyecto.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Colonia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private int numGatos;

    private Long registro;


    private Geolocalizacion localizacion;

    @OneToMany(mappedBy="id.colonia", fetch = FetchType.EAGER)
    private List<Alimentacion> alimentacion;
}
